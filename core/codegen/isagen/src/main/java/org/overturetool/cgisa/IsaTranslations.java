/*
 * #%~
 * VDM to Isabelle Translation
 * %%
 * Copyright (C) 2008 - 2015 Overture
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #~%
 */
package org.overturetool.cgisa;

import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.codegen.ir.*;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.*;
import org.overture.codegen.ir.expressions.*;
import org.overture.codegen.ir.types.*;
import org.overture.codegen.merging.MergeVisitor;
import org.overture.codegen.merging.TemplateCallable;
import org.overture.codegen.merging.TemplateManager;
import org.overture.config.Settings;
import org.overturetool.cgisa.utils.IsMethodTypeVisitor;
import org.overturetool.cgisa.utils.IsSeqOfCharTypeVisitor;
import org.overturetool.cgisa.utils.IsaInvNameFinder;
import org.overturetool.cgisa.utils.IsaSymbolFinder;

import java.io.StringWriter;
import java.util.*;

public class IsaTranslations {

    private static final String TEMPLATE_CALLABLE_NAME = "Isa";
    private static final String TYPE_PARAM_SEP = " \\<Rightarrow> ";
    private static final String LIST_SEP = ",";
    private static final String TUPLE_TYPE_SEPARATOR = "*";
    private static final String ISA_TEMPLATE_ROOT = "IsaTemplates";
    private final MergeVisitor mergeVisitor;

    protected IsaChecks isaUtils;

    public IsaTranslations() {
        TemplateCallable[] templateCallables = new TemplateCallable[]{new TemplateCallable(TEMPLATE_CALLABLE_NAME, this)};
        this.mergeVisitor = new MergeVisitor(new TemplateManager(ISA_TEMPLATE_ROOT, this.getClass()),templateCallables);
        this.isaUtils = new IsaChecks();
    }

    public MergeVisitor getMergeVisitor() {
        return mergeVisitor;
    }

    // Translations

    public String trans(INode node) throws AnalysisException {
        StringWriter writer = new StringWriter();
        node.apply(mergeVisitor, writer);
        if(node.getChildren(true).get("_tag") != null && node.getChildren(true).get("_tag").equals("ERR"))
            writer.append("\n (* Unable to translate *) ");
        return attachTranslationInfo((PIR) node, writer)
                .replace("true", "True")
                .replace("false", "False")
                .replace("error_ ", "error_X ")
                .replace("error_,", "error_X,");//hack around lower cased true, false and keywords.
    }

    private String attachTranslationInfo(PIR node, StringWriter writer) {
        if (node.getSourceNode() != null && node.getSourceNode().getVdmNode().getChildren(true)
                .get("_location") != null) {
            return writer.toString().replace("/*", "(*")
                    .replace("*/", "at start line: " +
                            ((ILexLocation) node.getSourceNode().getVdmNode().getChildren(true)
                                    .get("_location")).getStartLine()
                            + " in VDM source. *) \n");
        }
        else {
            return writer.toString();
        }
    }

    public String transNamedTypeDecl(ANamedTypeDeclIR n) throws AnalysisException {
        return n.getType().getNamedInvType() == null ? trans(n.getType()) : n.getType().getNamedInvType().getName().toString();
    }

    public String transNamedType(STypeIR n) throws AnalysisException {
        return IsaInvNameFinder.findName(n.clone());
    }

    public String transNamedQuoteType(AQuoteTypeIR n) throws AnalysisException {
        return n.getNamedInvType() == null ? n.getValue() : n.getNamedInvType().getName().toString();
    }

    public String transNamedMapType(AMapMapTypeIR n) throws AnalysisException {
        //either a map to a map to a map or a primitive to a primitive to a primitive
        if (n.parent() instanceof STypeIR && ((STypeIR) n.parent()).getNamedInvType() != null)
        {
            return ((AMapMapTypeIR) n.parent()).getNamedInvType().getName().toString();
        }
        else if (n.getNamedInvType() != null) {
            return n.getNamedInvType().getName().toString();
        }
        else
        {
            return trans(n.getFrom()) + " \\<rightharpoonup> " + trans(n.getTo());
        }
    }

    public String transMapEnum(AEnumMapExpIR node){
        NodeList<AMapletExpIR> maplets = (NodeList<AMapletExpIR>) node.getMembers();
        StringBuilder sb = new StringBuilder();
        maplets.forEach( m ->
                {
                    try {
                        if (sb.length() > 0) sb.append(", ");
                        sb.append(trans(m.getLeft()));
                        sb.append(" \\<mapsto> ");
                        sb.append(trans(m.getRight()));
                    } catch (AnalysisException e) {
                        e.printStackTrace();
                    }

                }
        );
        return sb.toString();
    }


    public String transUnion(STypeIR node) throws AnalysisException {
        return trans(node).replace("<", "").replace(">", "");//hack around lower cased trues;
    }



    public String transState(AStateDeclIR node) throws AnalysisException {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" ");
    	if (node != null) {
            if (node.getInvDecl() != null) {
                sb.append(trans(node.getInvDecl()) + "\n");
            }
	    	if (node.getInitDecl() != null) {
	    		sb.append(trans(node.getInitDecl()) + "\n");
	    	}
    	}
    	return sb.toString();
    }

	public String transApplyParams(List<SExpIR> params)
            throws AnalysisException {
        return transNodeList(params, " ");
    }

	public String transMkArgs(ANewExpIR node) {
		String str = "";
		List<SExpIR> args = new ArrayList<SExpIR>();
		args = node.getArgs();
		List<AFieldDeclIR> f = new ArrayList<AFieldDeclIR>();
		SDeclIR rs = IsaGen.rdeclGenHistoryMap.get(((ARecordTypeIR)
					node.getType().clone()).getName().toString());


		if (rs instanceof AStateDeclIR)
		{
			AStateDeclIR state = (AStateDeclIR) rs.clone();
			f = state.getFields();
			for (int i = 0; i < f.size(); i++)
			{
				str = str + state.getName().substring(0,1).toLowerCase()
						+ state.getName().substring(1) + "_"
						+ (f.get(i).getName() + " = " + args.get(i).toString());
				if (i < f.size()-1) str = str + (", ");
			}
		}
		else
		{
			ARecordDeclIR rec = (ARecordDeclIR) rs.clone();
			f = rec.getFields();
			for (int i = 0; i < args.size(); i++)
			{
				str = str + rec.getName().substring(0,1).toLowerCase()
						+ rec.getName().substring(1) + "_"
						+ (f.get(i).getName() + " = " + args.get(i).toString());
				if (i < f.size()-1) str = str + (", ");
			}
		}



		return str;
	}

    public String transTypeParams(List<STypeIR> params)
            throws AnalysisException {
        StringBuilder sb = new StringBuilder();
        Iterator<STypeIR> it = params.iterator();

        boolean l = params.size() >= 1 && ((AFuncDeclIR) params.get(0).parent().parent()).getName().contains("Option");

        while (it.hasNext()) {
            sb.append(trans(it.next().clone()));
            if(l) {
                sb.append(" option");
                l = false;
            }
            if (it.hasNext()) {
                sb.append(TYPE_PARAM_SEP);
            }
        }
        return sb.toString();
    }

    public String transBinds(List<? extends SMultipleBindIR> binds)
            throws AnalysisException {
        return transNodeList(binds, LIST_SEP);
    }

    public String mkFirstCharLowerCase(String x)
    {
        char[] c = x.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public String transNodeList(List<? extends INode> params, String sep)
            throws AnalysisException {

        StringBuilder sb = new StringBuilder();
        Iterator<? extends INode> it = params.iterator();

        while (it.hasNext()) {
            sb.append(trans((INode) it.next().clone()));
            if (it.hasNext()) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public String transString(List<SExpIR> args) throws AnalysisException {
        StringBuilder sb = new StringBuilder();
        sb.append("''");
        for (SExpIR arg : args) {
            sb.append(trans(arg));
        }
        sb.append("''");
        return sb.toString();
    }

    public String transSeq(List<SExpIR> args) throws AnalysisException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(transNodeList(args, LIST_SEP));
        sb.append("]");
        return sb.toString();
    }

    public String rec2Tuple(ARecordDeclIR record) throws AnalysisException {
        StringBuilder sb = new StringBuilder();

        Iterator<AFieldDeclIR> it = record.getFields().iterator();

        while (it.hasNext()) {
            AFieldDeclIR n = it.next();

            sb.append(trans(n.getType()));
            if (it.hasNext()) {
                sb.append(TUPLE_TYPE_SEPARATOR);
            }
        }

        return sb.toString();
    }

    // Hacks - translations that manipulate the tree in grostesque way due to
    // issues with the IR
    // FIXME Unhack result name extraction for implicit functions
    public String hackResultName(AFuncDeclIR func) throws AnalysisException {
        SourceNode x = func.getSourceNode();
        if (x.getVdmNode() instanceof AImplicitFunctionDefinition) {
            AImplicitFunctionDefinition iFunc = (AImplicitFunctionDefinition) x.getVdmNode();
            return iFunc.getResult().getPattern().toString();
        }
        throw new AnalysisException("Expected AFuncDeclIR in implicit function source. Got: "
                + x.getVdmNode().getClass().toString());
    }

    // FIXME Unhack invariant extraction for named types
    public String hackInv(ANamedTypeDeclIR type) {
        ATypeDeclIR tDecl = (ATypeDeclIR) type.parent();

        if (tDecl.getInv() != null) {
            AFuncDeclIR invFunc = (AFuncDeclIR) tDecl.getInv();
            StringBuilder sb = new StringBuilder();
            sb.append("inv ");
            sb.append(invFunc.getFormalParams().get(0).getPattern().toString());
            sb.append(" == ");
            sb.append(invFunc.getName());
            sb.append("(");
            sb.append("&");
            sb.append(invFunc.getFormalParams().get(0).getPattern().toString());
            sb.append(")");
            return sb.toString();
        }
        return "";
    }

    // FIXME Unhack invariant extraction for namedt ypes
    public String hackInv(ARecordDeclIR type) {

        if (type.getInvariant() != null) {
            AFuncDeclIR invFunc = (AFuncDeclIR) type.getInvariant();
            StringBuilder sb = new StringBuilder();
            sb.append("inv ");
            sb.append(invFunc.getFormalParams().get(0).getPattern().toString());
            sb.append(" == ");
            sb.append(invFunc.getName());
            sb.append("(");
            sb.append("&");
            sb.append(invFunc.getFormalParams().get(0).getPattern().toString());
            sb.append(")");
            return sb.toString();
        }
        return "";
    }

    public String hackInvDecl(ARecordDeclIR type) throws AnalysisException {
        if (type.getInvariant() != null) {
            return trans(type.getInvariant());
        }
        return "";
    }

    // Renamings

    public String norm(String name) {
        return name.replaceAll("-", "_");
    }

    public String varWrap(String v) {
        StringBuilder sb = new StringBuilder();
        sb.append('<');
        sb.append(v);
        sb.append('>');
        return sb.toString();
    }

    // Control flow

    public String filter(AFieldDeclIR field) throws AnalysisException {
        if (field.getFinal() && field.getStatic()) {
            return trans(field);
        }

        return "";
    }

    // Checks
    public boolean hasReturn(AMethodTypeIR node) {
        return !(node.getResult() instanceof AVoidTypeIR);
    }

    public boolean isRoot(INode node) {
        return isaUtils.isRoot(node);
    }

    public boolean isRootRec(AApplyExpIR node) {
        return isaUtils.isRootRec(node);
    }

    public String initial(SExpIR node) throws AnalysisException {
    	if (node.getClass() != AIdentifierVarExpIR.class &&
    			(node instanceof ASetSetTypeIR || node instanceof ASeqSeqTypeIR))
    	{
    		// Translate the collection symbols of the actual value in the abbreviation field
    		String initial = shift( Arrays.asList(transInit(node.getType(), node.toString().replace("[", "").replace("]", "")).split("")) );
    		return initial;
    	}
    	else
    	{
    		return trans(node);
    	}

    }

    // Hack around reverse recurion making VDMNat1 VDMSeq VDMSet appear as [{1}]
	private String shift(List<String> s) throws AnalysisException {

	    for (int i = 0; i < (s.size()/2)-1; i++)
	    {
	    	Collections.swap(s, i, i + 1);

	    }

	    for (int i = s.size()-1; i > (s.size()/2)+1; i--)
	    {
	    	Collections.swap(s, i, i - 1);

	    }

	    return s.toString().replace(", ", "").substring(1, s.size()+1);
	}

    private String transInit(STypeIR type, String val) throws AnalysisException {

    	if (type instanceof ASetSetTypeIR)
    	{
    		val = transInit( ((ASetSetTypeIR) type).getSetOf(), IsaSymbolFinder.findSymbol(type, val));
    	}
    	else if (type instanceof ASeqSeqTypeIR)
    	{
    		val = transInit( ((ASeqSeqTypeIR) type).getSeqOf(), IsaSymbolFinder.findSymbol(type, val));
    	}

		return val;
	}

	public boolean isString(STypeIR node) throws AnalysisException {
        return node.apply(new IsSeqOfCharTypeVisitor());
    }

    public boolean isFunc(STypeIR node) throws AnalysisException {
        return node.apply(new IsMethodTypeVisitor());
    }

    public boolean isRecordDecl(ATypeDeclIR node) {
        return (node.getDecl() instanceof ARecordDeclIR);
    }

    public boolean isTokenType(ANamedTypeDeclIR node){
        return (node.getType() instanceof ATokenBasicTypeIR);
    }

    public boolean isUnionType(ANamedTypeDeclIR node){
        return (node.getType() instanceof AUnionTypeIR);
    }

    public boolean isDataType(ANamedTypeDeclIR node){
        return (node.getType() instanceof AUnionTypeIR || node.getType() instanceof AQuoteTypeIR);
    }

    public boolean hasInvariant(ATypeDeclIR node) {
        return (node.getInv() != null);
    }

    public String genUnaryTypeConstructorInv(Object node, String name)
    {
        if(node instanceof ASeqSeqTypeIR ||
            node instanceof ASetSetTypeIR)
        {
            String inv = "";
            if(node instanceof ASeqSeqTypeIR)
            {
                ASeqSeqTypeIR node_ = (ASeqSeqTypeIR) node;
                if(node_.getSeqOf() instanceof SBasicTypeBase)
                {
                    // In this case it is a seq of a basic type.
                    inv= "inv_SeqElems invTrue";
                }
            }

            return String.join(" ", inv, name);
        }
        else {
            return "";
        }
    }

    public boolean isInvariant(AFuncDeclIR node)
    {
        if (node.parent() != null) {
            if(node.parent() instanceof ATypeDeclIR) {
                ATypeDeclIR p = (ATypeDeclIR) node.parent();
                return p.getInv() == node;
            }
        }
        return false;
    }

    public boolean isUnaryTypeConstructor(Object node)
    {
        return node instanceof ASeqSeqTypeIR ||
                node instanceof ASetSetTypeIR;
    }

    public boolean isAppliedToBasicType(ASeqSeqTypeIR node)
    {
        return node.getSeqOf() instanceof SBasicTypeIR;
    }

    public boolean isAppliedToBasicType(ASetSetTypeIR node)
    {
        return node.getSetOf() instanceof SBasicTypeIR;
    }

    public String concreteTypeInvariantForUnaryTypeConstructorInvariant(STypeIR t, String prefix)
    {
        if (t instanceof SBasicTypeIR)
        {
            SBasicTypeIR t_ = (SBasicTypeIR) t;
            return prefix+"isa_invTrue";
        }
        else {
            return prefix+"inv_" + t.getNamedInvType().getName();
        }
    }

    public String genInvariantsForUnaryTypeConstructor(Object node)
    {

    	if(node instanceof ASeqSeqTypeIR){
            ASeqSeqTypeIR node_ = (ASeqSeqTypeIR) node;
            return concreteTypeInvariantForUnaryTypeConstructorInvariant(node_.getSeqOf(),"inv_SeqElems ");
        }
        if(node instanceof ASetSetTypeIR)
        {
            ASetSetTypeIR node_ = (ASetSetTypeIR) node;
            return concreteTypeInvariantForUnaryTypeConstructorInvariant(node_.getSetOf(), "inv_SetElems");
        }
        return "genInvariantsForUnaryTypeConstructor found no match for node: " + node;
    }

    public String genInvariantsForRecordDecl(ARecordDeclIR node)
    {
        List<String> invs = new ArrayList<>();
        for (AFieldDeclIR f : node.getFields())
        {
            Object type = f.getType();
            if(isUnaryTypeConstructor(type))
            {
                invs.add(genInvariantsForUnaryTypeConstructor(type));
            }
        }
        return String.join("\\<and>", invs);
    }

    public String transEquiv(AEqualsBinaryExpIR n) throws AnalysisException {

        return n.parent() instanceof SExpIR ? trans(n.getLeft()) + " = " + trans(n.getRight())
                : trans(n.getLeft()) + " <\\equiv> " + trans(n.getRight());
    }

    public String transOriginalVDM(SDeclIR n) throws AnalysisException {
        if(Settings.vdmcomments) {
            if (n instanceof ANamedTypeDeclIR && isDataType((ANamedTypeDeclIR) n)
            && n.getSourceNode().getVdmNode().getChildren(true).get("_type") != null) {
                return "(* Below translated from VDM node:\n-----------------------------------------------\n" +
                        IsaInvNameFinder.findName(n) + " = " +
                        n.getSourceNode().getVdmNode().getChildren(true).get("_type").toString() + "\n*)";
            } else {
                return "(* Below translated from VDM node:\n-----------------------------------------------\n"
                        + n.getSourceNode().getVdmNode().toString()+ "\n*)";
            }
        }
        else{
            return "";
        }
    }

    public boolean addOption(ANamedTypeDeclIR n) throws AnalysisException {
        return IsaInvNameFinder.findName(n).contains("Option");
    }

    //A utility method for examining values as they are passed in velocity
    public String peek(ARecordDeclIR node){
        return ("");
    }

}

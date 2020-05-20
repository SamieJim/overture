package org.overturetool.cgisa.transformations;

import org.overture.ast.util.ClonableString;
import org.overture.cgisa.isair.analysis.DepthFirstAnalysisIsaAdaptor;
import org.overture.codegen.ir.*;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.*;
import org.overture.codegen.ir.expressions.*;
import org.overture.codegen.ir.name.ATypeNameIR;
import org.overture.codegen.ir.patterns.AIdentifierPatternIR;
import org.overture.codegen.ir.types.ABoolBasicTypeIR;
import org.overture.codegen.ir.types.AMethodTypeIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;
import org.overturetool.cgisa.IsaGen;
import org.overturetool.cgisa.utils.IsaAddMetaData;
import org.overturetool.cgisa.utils.IsaInvNameFinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IsaOptionalConv extends DepthFirstAnalysisIsaAdaptor {

	private final Map<String, ATypeDeclIR> isaTypeDeclIRMap;
	private final TransAssistantIR t;
	private final AModuleDeclIR vdmToolkitModuleIR;
	private final IRInfo info;

	public IsaOptionalConv(IRInfo info, TransAssistantIR t, AModuleDeclIR vdmToolkitModuleIR) {
		this.t = t;
		this.info = info;
		this.vdmToolkitModuleIR = vdmToolkitModuleIR;

		this.isaTypeDeclIRMap = this.vdmToolkitModuleIR.getDecls()
				.stream()
				.filter(d -> {
					if (d instanceof ATypeDeclIR)
						return true;
					else
						return false;
				}).map(d -> (ATypeDeclIR) d)
				.collect(Collectors.toMap(x -> ((ANamedTypeDeclIR) x.getDecl()).getName().getName(), x -> x));
	}



	public void caseAFieldDeclIR(AFieldDeclIR node) throws AnalysisException {
		if(node.getType().getOptional() != null && node.getType().getOptional()) {
			ATypeDeclIR p = new ATypeDeclIR();
			AFieldDeclIR x = node.clone();
			ANamedTypeDeclIR opt = new ANamedTypeDeclIR();
			opt.setSourceNode((SourceNode) node.parent().getChildren(true).get("_sourceNode"));
			ATypeNameIR t = new ATypeNameIR();
			if(IsaInvNameFinder.findName(x.getType()).contains("Option")) {
				IrNodeInfo w = new IrNodeInfo(node, "Option is a keyword reserved for translation of optional types. Consider changing your naming to avoid translation errors.");
				this.info.getTransformationWarnings().add(w);
			}
			t.setName(IsaInvNameFinder.findName(x.getType()) + "Option");
			opt.setName(t);
			opt.setType(IsaDeclTypeGen.apply(node));
			p.setDecl(opt.clone());
			List<ClonableString> metaData = new LinkedList<>();
			p.setMetaData(metaData);
			p.setSourceNode((SourceNode) node.parent().getChildren(true).get("_sourceNode"));

			if(node.getAncestor(AStateDeclIR.class) != null){
				int ix = Integer.parseInt(node.getAncestor(AStateDeclIR.class).getMetaData().get(0).toString().substring(15));
				addToAST(p, node, ix);
			}
			else{
				int ix = Integer.parseInt(node.getAncestor(ATypeDeclIR.class).getMetaData().get(0).toString().substring(15));
				addToAST(p, node, ix);
			}


			node.setType(IsaDeclTypeGen.apply(opt));

			System.out.println("Generated optional type " + node.getName() + " has been assigned to type synonym " + node.getName() + "Option.");
		}
	}

	private void addToAST(INode node, SDeclIR parent, int ix) throws AnalysisException {
		// Insert into AST
		AModuleDeclIR encModule = parent.getAncestor(AModuleDeclIR.class) != null
				? parent.getAncestor(AModuleDeclIR.class).clone() : null;
		node.parent(parent.clone());
		IsaAddMetaData o = new IsaAddMetaData(this.info, this.t, this.vdmToolkitModuleIR);
		o.apply(node);
		if(encModule != null)
		{
			parent.getAncestor(AModuleDeclIR.class).getDecls().add(ix, (SDeclIR) node.clone());
		}
	}
    
}

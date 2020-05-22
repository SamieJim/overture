package org.overturetool.cgisa.transformations;

import org.overture.ast.util.ClonableString;
import org.overture.cgisa.isair.analysis.DepthFirstAnalysisIsaAdaptor;
import org.overture.codegen.ir.*;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.*;
import org.overture.codegen.ir.name.ATypeNameIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;
import org.overturetool.cgisa.utils.IsaInvNameFinder;

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
			opt.setType(IsaDeclTypeGen.apply(x.clone()).clone());
			p.setDecl(opt.clone());

			x.setType(IsaDeclTypeGen.apply(opt.clone()));
			for (SDeclIR d : node.getAncestor(AModuleDeclIR.class).getDecls()) {
				try {
					if (IsaInvNameFinder.findName(d).equals(t.getName().replace("Option", "")))
						p.setSourceNode(d.getSourceNode());
				} catch (AnalysisException e) {
					e.printStackTrace();
				}
			}
			x.setSourceNode(node.getSourceNode());

			if(node.parent() instanceof  ARecordDeclIR) {
				((ARecordDeclIR) node.parent()).getFields().remove(node.clone());
				((ARecordDeclIR) node.parent()).getFields().add(x.clone());
			}
			else if(node.parent() instanceof AStateDeclIR){
				((AStateDeclIR) node.parent()).getFields().remove(node.clone());
				((AStateDeclIR) node.parent()).getFields().add(x.clone());
			}
			node.setType(IsaDeclTypeGen.apply(opt.clone()));

			addToAST(p, node);
			System.out.println("Generated optional type " + node.getName() + " has been assigned to type synonym " + node.getName() + "Option.");
		}
	}

	private void addToAST(ATypeDeclIR node, SDeclIR parent) throws AnalysisException {
		// Insert into AST
		AModuleDeclIR encModule = parent.getAncestor(AModuleDeclIR.class) != null
				? parent.getAncestor(AModuleDeclIR.class).clone() : null;
		if(encModule != null)
		{
			parent.getAncestor(AModuleDeclIR.class).getDecls().add(0, (SDeclIR) node.clone());
		}
	}
    
}

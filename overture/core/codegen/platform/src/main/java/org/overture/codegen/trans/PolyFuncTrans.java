package org.overture.codegen.trans;

import org.apache.log4j.Logger;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.node.INode;
import org.overture.codegen.assistant.AssistantBase;
import org.overture.codegen.ir.SExpIR;
import org.overture.codegen.ir.STypeIR;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor;
import org.overture.codegen.ir.declarations.AFormalParamLocalParamIR;
import org.overture.codegen.ir.declarations.AFuncDeclIR;
import org.overture.codegen.ir.expressions.AApplyExpIR;
import org.overture.codegen.ir.expressions.AExplicitVarExpIR;
import org.overture.codegen.ir.expressions.AIdentifierVarExpIR;
import org.overture.codegen.ir.expressions.AMethodInstantiationExpIR;
import org.overture.codegen.ir.expressions.AQuoteLiteralExpIR;
import org.overture.codegen.ir.expressions.ATypeArgExpIR;
import org.overture.codegen.ir.types.*;
import org.overture.codegen.trans.assistants.TransAssistantIR;

public class PolyFuncTrans extends DepthFirstAnalysisAdaptor {

    protected Logger log = Logger.getLogger(this.getClass().getName());

    private TransAssistantIR assist;

    private static final String TYPE_ARG_PREFIX = "_type_";

    private static final String UTIL_CLASS = "Utils";
    private static final String SET_UTIL = "SetUtil";
    private static final String VDM_SET = "VDMSet";
    private static final String SET_METHOD_NAME = "set";


    public static final String OBJECT = "Object";

    public static final String NAT = "NAT";
    public static final String NAT1 = "NAT1";
    public static final String INT = "INT";
    public static final String REAL = "REAL";
    public static final String RAT = "RAT";
    public static final String BOOL = "BOOL";
    public static final String CHAR = "CHAR";
    public static final String TOKEN = "TOKEN";
    public static final String STRING = "STRING";
    public static final String UNKNOWN = "UNKNOWN";

    public static final String TYPE_NOT_SUPPORTED = "TYPE_NOT_SUPPORTED";

    public PolyFuncTrans(TransAssistantIR assist)
    {
        this.assist = assist;
    }

    @Override
    public void caseAFuncDeclIR(AFuncDeclIR node) throws AnalysisException {

        super.caseAFuncDeclIR(node);

        if (!node.getTemplateTypes().isEmpty()) {

            for(ATemplateTypeIR t : node.getTemplateTypes())
            {
                AFormalParamLocalParamIR param = new AFormalParamLocalParamIR();
                param.setType(new AUnknownTypeIR());
                param.setPattern(assist.getInfo().getPatternAssistant().consIdPattern(toTypeArgName(t)));

                node.getFormalParams().add(param);
            }
        }
    }

    public static String toTypeArgName(ATemplateTypeIR t) {

        return TYPE_ARG_PREFIX + t;
    }

    @Override
    public void caseAApplyExpIR(AApplyExpIR node) throws AnalysisException {

        super.caseAApplyExpIR(node);

        SExpIR root = node.getRoot();

        if(root instanceof AMethodInstantiationExpIR)
        {
            AMethodInstantiationExpIR methodInst = (AMethodInstantiationExpIR) root;

            SExpIR func = methodInst.getFunc();

            if(func instanceof AExplicitVarExpIR)
            {
                AExplicitVarExpIR ev = (AExplicitVarExpIR) func;

                STypeIR classType = ev.getClassType();

                if(classType instanceof AClassTypeIR)
                {
                    if(assist.getInfo().getDeclAssistant().isLibraryName(((AClassTypeIR) classType).getName()))
                    {
                        // Libraries don't expect type arguments
                        return;
                    }
                }
            }
            else if(func instanceof AIdentifierVarExpIR)
            {
                INode var = AssistantBase.getVdmNode(func);

                if(var instanceof AVariableExp) {
                    PDefinition def = ((AVariableExp) var).getVardef();

                    if (def instanceof ARenamedDefinition) {
                        ARenamedDefinition renamedDef = (ARenamedDefinition) def;

                        if (assist.getInfo().getDeclAssistant().isLibraryName(renamedDef.getDef().getName().getModule())) {

                            // Libraries don't expect type arguments
                            return;
                        }
                    }

                }
            }

            for(STypeIR type : methodInst.getActualTypes())
            {
                if(type instanceof AQuoteTypeIR)
                {
                    AQuoteLiteralExpIR qt = new AQuoteLiteralExpIR();
                    qt.setValue(((AQuoteTypeIR) type).getValue());
                    node.getArgs().add(qt);
                }
                else if(type instanceof ARecordTypeIR)
                {
                    ATypeArgExpIR typeArg = new ATypeArgExpIR();
                    typeArg.setType(type.clone());
                    node.getArgs().add(typeArg);
                }
                else if(type instanceof ATemplateTypeIR)
                {
                    ATemplateTypeIR templateType = (ATemplateTypeIR) type;
                    String paramName = toTypeArgName(templateType);
                    AIdentifierVarExpIR templateTypeArg = assist.getInfo().getExpAssistant().consIdVar(paramName, templateType.clone());
                    node.getArgs().add(templateTypeArg);
                }
                else if(type instanceof AUnionTypeIR)
                {
                    AUnionTypeIR unionType = (AUnionTypeIR) type;

                    if(assist.getInfo().getTypeAssistant().isUnionOfType(unionType, AQuoteTypeIR.class))
                    {
                        AExplicitVarExpIR setConstructorMember = consTypeArg(getSetUtil());
                        setConstructorMember.setName(getSetMethodName());

                        AExternalTypeIR setType = new AExternalTypeIR();
                        setType.setName(getVdmSet());

                        AApplyExpIR setConstructor = new AApplyExpIR();
                        setConstructor.setType(setType);
                        setConstructor.setRoot(setConstructorMember);

                        for(STypeIR t : unionType.getTypes())
                        {
                            AQuoteLiteralExpIR qt = new AQuoteLiteralExpIR();
                            qt.setType(t.clone());
                            if(t instanceof AQuoteTypeIR)
                            {
                                qt.setValue(((AQuoteTypeIR) t).getValue());
                            }
                            else
                            {
                                // Should never happen
                                log.warn("Expected type to be a quote type but got " + t);
                            }
                            setConstructor.getArgs().add(qt);
                        }

                        node.getArgs().add(setConstructor);

                    }
                    else {
                        issueUnsupportedWarning(methodInst);
                        AExplicitVarExpIR typeArg = consTypeArg(getUtilClass());
                        String name = getUnsupportedTypeFieldName();
                        typeArg.setName(name);
                        node.getArgs().add(typeArg);
                    }
                }
                else
                {
                    AExplicitVarExpIR typeArg = consTypeArg(getUtilClass());

                    String name;
                    if(type instanceof ANatNumericBasicTypeIR)
                    {
                        name = NAT;
                    }
                    else if(type instanceof ANat1NumericBasicTypeIR)
                    {
                        name = NAT1;
                    }
                    else if(type instanceof AIntNumericBasicTypeIR)
                    {
                        name = INT;
                    }
                    else if(type instanceof ARealNumericBasicTypeIR)
                    {
                        name = REAL;
                    }
                    else if(type instanceof ARatNumericBasicTypeIR)
                    {
                        name = RAT;
                    }
                    else if(type instanceof ABoolBasicTypeIR)
                    {
                        name = BOOL;
                    }
                    else if(type instanceof ACharBasicTypeIR)
                    {
                        name = CHAR;
                    }
                    else if(type instanceof ATokenBasicTypeIR)
                    {
                        name = TOKEN;
                    }
                    else if(type instanceof AStringTypeIR)
                    {
                        name = STRING;
                    }
                    else if(type instanceof AUnknownTypeIR)
                    {
                        name = UNKNOWN;
                    }
                    else
                    {
                        issueUnsupportedWarning(methodInst);
                        name = getUnsupportedTypeFieldName();
                    }
                    typeArg.setName(name);

                    node.getArgs().add(typeArg);
                }
            }
        }
    }

    public AExplicitVarExpIR consTypeArg(String className) {

        AExternalTypeIR runtimeUtilClass = new AExternalTypeIR();
        runtimeUtilClass.setName(className);

        AExternalTypeIR anyType = new AExternalTypeIR();
        anyType.setName(getTypeArgumentFieldName());

        AExplicitVarExpIR typeArg = new AExplicitVarExpIR();
        typeArg.setClassType(runtimeUtilClass);
        typeArg.setIsLocal(false);
        typeArg.setIsLambda(false);
        typeArg.setType(anyType);
        return typeArg;
    }

    public void issueUnsupportedWarning(AMethodInstantiationExpIR methodInst) {
        assist.getInfo().addTransformationWarning(methodInst, "Function instantiation only " +
                "works for basic types, quotes, union of quotes, strings, polymorphic types and records");
    }

    public String getUnsupportedTypeFieldName() {
        return TYPE_NOT_SUPPORTED;
    }

    public String getTypeArgumentFieldName() {
        return OBJECT;
    }

    public String getUtilClass() {
        return UTIL_CLASS;
    }

    public String getSetUtil(){
        return SET_UTIL;
    }

    public String getVdmSet()
    {
        return VDM_SET;
    }

    public String getSetMethodName(){
        return SET_METHOD_NAME;
    }
}

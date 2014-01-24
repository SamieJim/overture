package org.overture.typechecker.assistant.type;

import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

public class AProductTypeAssistantTC
{
	protected static ITypeCheckerAssistantFactory af;

	@SuppressWarnings("static-access")
	public AProductTypeAssistantTC(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

//	public static PType typeResolve(AProductType type, ATypeDefinition root,
//			IQuestionAnswer<TypeCheckInfo, PType> rootVisitor,
//			TypeCheckInfo question)
//	{
//
//		if (type.getResolved())
//			return type;
//		else
//		{
//			type.setResolved(true);
//		}
//
//		try
//		{
//			List<PType> fixed = new Vector<PType>();
//
//			for (PType t : type.getTypes())
//			{
//				PType rt = af.createPTypeAssistant().typeResolve(t, root, rootVisitor, question);
//				fixed.add(rt);
//			}
//
//			type.setTypes(fixed);
//			return type;
//		} catch (TypeCheckException e)
//		{
//			unResolve(type);
//			throw e;
//		}
//	}
//
//	public static void unResolve(AProductType type)
//	{
//		if (!type.getResolved())
//			return;
//		else
//		{
//			type.setResolved(false);
//		}
//
//		for (PType t : type.getTypes())
//		{
//			PTypeAssistantTC.unResolve(t);
//		}
//	}

	// public static String toDisplay(AProductType exptype) {
	// return Utils.listToString("(", exptype.getTypes(), " * ", ")");
	// }

//	public static boolean isProduct(AProductType type, int size)
//	{
//		return size == 0 || type.getTypes().size() == size;
//	}

//	public static AProductType getProduct(AProductType type, int n)
//	{
//		return n == 0 || type.getTypes().size() == n ? type : null;
//	}

	// public static boolean equals(AProductType type, Object other) {
	// other = PTypeAssistantTC.deBracket(other);
	//
	// if (other instanceof AProductType)
	// {
	// AProductType pother = (AProductType)other;
	// return PTypeAssistantTC.equals(type.getTypes(),pother.getTypes());
	// }
	//
	// return false;
	// }

	// public static AProductType getProduct(AProductType type) {
	// return type;
	// }

//	public static boolean isProduct(AProductType type)
//	{
//		return true;
//	}

//	public static PType polymorph(AProductType type, ILexNameToken pname,
//			PType actualType)
//	{
//		List<PType> polytypes = new Vector<PType>();
//
//		for (PType ptype : ((AProductType) type).getTypes())
//		{
//			polytypes.add(PTypeAssistantTC.polymorph(ptype, pname, actualType));
//		}
//
//		return AstFactory.newAProductType(type.getLocation(), polytypes);
//	}

}
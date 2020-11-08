package org.overture.codegen.vdm2jml.predgen.info;

import org.apache.log4j.Logger;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.types.*;
import org.overture.codegen.ir.IRInfo;
import org.overture.codegen.ir.STypeIR;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NamedTypeInvDepCalculator extends DepthFirstAnalysisAdaptor
{
	private List<NamedTypeInfo> typeInfoList;
	private IRInfo info;

	private static Logger log = Logger.getLogger(NamedTypeInvDepCalculator.class.getName());

	public NamedTypeInvDepCalculator(IRInfo info)
	{
		super();
		this.info = info;
		this.typeInfoList = new LinkedList<NamedTypeInfo>();
	}

	public List<NamedTypeInfo> getTypeDataList()
	{
		return typeInfoList;
	}

	public static NamedTypeInfo findTypeInfo(List<NamedTypeInfo> typeInfoList,
			String defModule, String typeName)
	{
		for (NamedTypeInfo t : typeInfoList)
		{
			if (NamedTypeInfo.isSameTypeDef(t, defModule, typeName))
			{
				return t;
			}
		}

		return null;
	}

	public boolean containsExactly(ANamedInvariantType node)
	{
		String module = node.getName().getModule();
		String typeName = node.getName().getName();

		for (NamedTypeInfo t : typeInfoList)
		{
			if (NamedTypeInfo.isSameTypeDef(t, module, typeName))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void caseANamedInvariantType(ANamedInvariantType node)
			throws AnalysisException
	{
		// Avoid unnecessary construction
		if (!containsExactly(node))
		{
			AbstractTypeInfo typeInfo = create(info, node, new HashSet<PType>());

			if (typeInfo instanceof NamedTypeInfo)
			{
				typeInfoList.add((NamedTypeInfo) typeInfo);
			} else
			{
				log.error("Expected a '" + NamedTypeInfo.class.getSimpleName()
						+ "' to be returned. Got: " + typeInfo);
			}
		}
	}

	private static AbstractTypeInfo create(IRInfo info, PType type,
			Set<PType> visited)
	{
		if (visited.contains(type))
		{
			// Type recursion
			return new RecursiveLeaf();
		} else
		{
			visited.add(type);
		}

		boolean optional = false;
		while (type instanceof AOptionalType || type instanceof ABracketType)
		{
			if (type instanceof AOptionalType)
			{
				type = ((AOptionalType) type).getType();
				optional = true;
			} else if (type instanceof ABracketType)
			{
				type = ((ABracketType) type).getType();
			}
		}

		if (type instanceof ANamedInvariantType)
		{
			ANamedInvariantType namedType = (ANamedInvariantType) type;

			AbstractTypeInfo domainInfo = create(info, namedType.getType(), visited);
			NamedTypeInfo namedInfo = new NamedTypeInfo(namedType.getName().getName(), namedType.getName().getModule(), namedType.getInvDef() != null, optional, domainInfo);

			return namedInfo;

		} else if (type instanceof AUnionType)
		{
			List<AbstractTypeInfo> types = new LinkedList<>();

			for (PType t : ((AUnionType) type).getTypes())
			{
				AbstractTypeInfo tInfo = create(info, t, visited);

				if (tInfo != null)
				{
					types.add(tInfo);
				}
			}

			return new UnionInfo(optional, types);

		} else if (type instanceof AProductType)
		{
			List<AbstractTypeInfo> types = new LinkedList<>();

			for (PType t : ((AProductType) type).getTypes())
			{
				AbstractTypeInfo tInfo = create(info, t, visited);

				if (tInfo != null)
				{
					types.add(tInfo);
				}
			}

			return new TupleInfo(optional, types);
		} else if (type instanceof SSeqTypeBase)
		{
			SSeqTypeBase seqType = (SSeqTypeBase) type;
			boolean isSeq1 = seqType instanceof ASeq1SeqType;

			return new SeqInfo(optional, create(info, seqType.getSeqof(), visited), isSeq1);
		} else if (type instanceof SSetType)
		{
			SSetType setType = (SSetType) type;

			return new SetInfo(optional, create(info, setType.getSetof(), visited));
		} else if (type instanceof SMapTypeBase)
		{
			SMapTypeBase mapType = (SMapTypeBase) type;

			AbstractTypeInfo fromInfo = create(info, mapType.getFrom(), visited);
			AbstractTypeInfo toInfo = create(info, mapType.getTo(), visited);

			boolean injective = type instanceof AInMapMapType;

			return new MapInfo(optional, fromInfo, toInfo, injective);
		} else if (type instanceof AUnknownType)
		{
			return new UnknownLeaf();
		} else
		{
			return new LeafTypeInfo(toIrType(type, info), optional);
		}
	}

	public static STypeIR toIrType(PType type, IRInfo info)
	{
		try
		{
			STypeIR irType = type.apply(info.getTypeVisitor(), info);

			if (irType != null)
			{
				irType.setOptional(false);
			}

			return irType;

		} catch (AnalysisException e)
		{
			log.error("Problems encountered while attempting "
					+ "to construct the IR type from a VDM type: "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

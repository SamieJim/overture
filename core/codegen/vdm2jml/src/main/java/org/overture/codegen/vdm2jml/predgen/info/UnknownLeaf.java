package org.overture.codegen.vdm2jml.predgen.info;

import org.overture.codegen.vdm2jml.util.NameGen;

import java.util.LinkedList;
import java.util.List;

public class UnknownLeaf extends AbstractTypeInfo
{
	public UnknownLeaf()
	{
		super(false);
	}

	@Override
	public List<LeafTypeInfo> getLeafTypesRecursively()
	{
		return new LinkedList<>();
	}

	@Override
	public String consCheckExp(String enclosingClass, String javaRootPackage,
			String arg, NameGen nameGen)
	{
		return "true";
	}
}

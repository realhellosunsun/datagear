/*
 * Copyright (c) 2018 datagear.org. All Rights Reserved.
 */

package org.datagear.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件工具类。
 * 
 * @author datagear@163.com
 *
 */
public class FileUtil
{
	private FileUtil()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取文件对象。
	 * 
	 * @param file
	 * @return
	 */
	public static File getFile(String file)
	{
		return new File(file);
	}

	/**
	 * 获取指定目录下的文件对象。
	 * 
	 * @param parent
	 * @param file
	 * @return
	 */
	public static File getFile(File parent, String file)
	{
		return new File(parent, file);
	}

	/**
	 * 删除文件。
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file)
	{
		if (!file.exists())
			return true;

		if (file.isDirectory())
		{
			File[] children = file.listFiles();

			for (File child : children)
				deleteFile(child);
		}

		return file.delete();
	}

	/**
	 * 清空目录，保留目录本身。
	 * 
	 * @param directory
	 * @return
	 */
	public static boolean clearDirectory(File directory)
	{
		if (!directory.exists())
			return true;

		boolean clear = true;

		if (directory.isDirectory())
		{
			File[] children = directory.listFiles();

			for (File child : children)
			{
				boolean deleted = deleteFile(child);

				if (!deleted && clear)
					clear = false;
			}
		}

		return clear;
	}

	/**
	 * 在指定目录下生成一个文件。
	 * 
	 * @param parent
	 * @return
	 */
	public static File generateUniqueFile(File parent)
	{
		return new File(parent, IDUtil.uuid());
	}

	/**
	 * 在指定目录下生成一个子文件夹。
	 * 
	 * @param parent
	 * @return
	 */
	public static File generateUniqueDirectory(File parent)
	{
		File file = new File(parent, IDUtil.uuid());

		if (!file.exists())
			file.mkdirs();

		return file;
	}

	/**
	 * 列出指定目录下的文件名称。
	 * 
	 * @param directory
	 * @return
	 */
	public static String[] listFileNames(File directory)
	{
		if (!directory.exists())
			return new String[0];

		return directory.list();
	}

	/**
	 * 获取指定文件的{@linkplain FileInfo}。
	 * 
	 * @param file
	 * @return
	 */
	public static FileInfo getFileInfo(File file)
	{
		FileInfo fileInfo = new FileInfo(file.getName());

		if (file.exists())
			fileInfo.setBytes(file.length());

		return fileInfo;
	}

	/**
	 * 获取指定目录下文件的{@linkplain FileInfo}。
	 * 
	 * @param directory
	 * @return
	 */
	public static FileInfo[] getFileInfos(File directory)
	{
		if (!directory.exists())
			return new FileInfo[0];

		File[] files = directory.listFiles();

		FileInfo[] fileInfos = new FileInfo[files.length];

		for (int i = 0; i < files.length; i++)
			fileInfos[i] = getFileInfo(files[i]);

		return fileInfos;
	}

	/**
	 * 获取{@linkplain File}的{@linkplain URL}。
	 * 
	 * @param file
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static URL toURL(File file) throws IllegalArgumentException
	{
		try
		{
			return file.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			throw new IllegalArgumentException("Illegal [" + file.toString() + "] to URL");
		}
	}
}
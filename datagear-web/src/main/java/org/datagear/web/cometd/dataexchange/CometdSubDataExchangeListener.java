/*
 * Copyright (c) 2018 datagear.org. All Rights Reserved.
 */

package org.datagear.web.cometd.dataexchange;

import java.util.Locale;

import org.cometd.bayeux.server.ServerChannel;
import org.datagear.dataexchange.DataExchangeException;
import org.datagear.dataexchange.DataExchangeListener;
import org.springframework.context.MessageSource;

/**
 * 基于Cometd的子数据交换{@linkplain DataExchangeListener}。
 * 
 * @author datagear@163.com
 *
 */
public abstract class CometdSubDataExchangeListener extends CometdDataExchangeListener
{
	private String subDataExchangeId;

	public CometdSubDataExchangeListener()
	{
		super();
	}

	public CometdSubDataExchangeListener(DataExchangeCometdService dataExchangeCometdService,
			ServerChannel dataExchangeServerChannel, MessageSource messageSource, Locale locale,
			String subDataExchangeId)
	{
		super(dataExchangeCometdService, dataExchangeServerChannel, messageSource, locale);
		this.subDataExchangeId = subDataExchangeId;
	}

	public String getSubDataExchangeId()
	{
		return subDataExchangeId;
	}

	public void setSubDataExchangeId(String subDataExchangeId)
	{
		this.subDataExchangeId = subDataExchangeId;
	}

	@Override
	protected DataExchangeMessage buildStartMessage()
	{
		return new SubStart(this.subDataExchangeId);
	}

	@Override
	protected DataExchangeMessage buildExceptionMessage(DataExchangeException e)
	{
		return new SubException(this.subDataExchangeId, resolveDataExchangeExceptionI18n(e), evalDuration());
	}

	@Override
	protected DataExchangeMessage buildSuccessMessage()
	{
		return new SubSuccess(this.subDataExchangeId, evalDuration());
	}

	@Override
	protected DataExchangeMessage buildFinishMessage()
	{
		return new SubFinish(this.subDataExchangeId);
	}

	/**
	 * 子数据交换开始。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class SubStart extends SubDataExchangeMessage
	{
		public SubStart()
		{
			super();
		}

		public SubStart(String subDataExchangeId)
		{
			super(subDataExchangeId);
		}
	}

	/**
	 * 子数据交换异常。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class SubException extends SubDataExchangeMessage
	{
		private String content;

		private long duration;

		public SubException()
		{
			super();
		}

		public SubException(String subDataExchangeId, String content, long duration)
		{
			super(subDataExchangeId);
			this.content = content;
			this.duration = duration;
		}

		public String getContent()
		{
			return content;
		}

		public void setContent(String content)
		{
			this.content = content;
		}

		public long getDuration()
		{
			return duration;
		}

		public void setDuration(long duration)
		{
			this.duration = duration;
		}
	}

	/**
	 * 子数据交换成功。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class SubSuccess extends SubDataExchangeMessage
	{
		private long duration;

		public SubSuccess()
		{
			super();
		}

		public SubSuccess(String subDataExchangeId, long duration)
		{
			super(subDataExchangeId);
			this.duration = duration;
		}

		public long getDuration()
		{
			return duration;
		}

		public void setDuration(long duration)
		{
			this.duration = duration;
		}
	}

	/**
	 * 子数据交换完成。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class SubFinish extends SubDataExchangeMessage
	{
		public SubFinish()
		{
			super();
		}

		public SubFinish(String subDataExchangeId)
		{
			super(subDataExchangeId);
		}
	}
}
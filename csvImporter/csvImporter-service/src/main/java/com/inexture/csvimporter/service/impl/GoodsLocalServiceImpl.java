/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.inexture.csvimporter.service.impl;

import com.inexture.csvimporter.model.Goods;
import com.inexture.csvimporter.model.impl.GoodsImpl;
import com.inexture.csvimporter.service.base.GoodsLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the goods local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.inexture.csvimporter.service.GoodsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GoodsLocalServiceBaseImpl
 * @see com.inexture.csvimporter.service.GoodsLocalServiceUtil
 */
public class GoodsLocalServiceImpl extends GoodsLocalServiceBaseImpl {
	private static final Log log = LogFactoryUtil.getLog(GoodsLocalServiceImpl.class.getName());

	/**
	 * Method to persist goods object
	 * 
	 * @param goodsobj
	 * @return
	 */
	public Goods add(Goods goodsobj) {
		log.debug("Saving.." + goodsobj);
		Goods goods = createGoods(counterLocalService.increment(GoodsImpl.class.getName()));
		goods.setName(goodsobj.getName());
		goods.setDescription(goodsobj.getDescription());
		goods.setLength(goodsobj.getLength());
		goods.setHeight(goodsobj.getHeight());
		goods.setArea(goodsobj.getHeight() * goodsobj.getLength());
		goods.setNr(goodsobj.getNr());
		return goodsLocalService.addGoods(goods);
	}
}
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

package com.inexture.csvimporter.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GoodsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GoodsLocalService
 * @generated
 */
@ProviderType
public class GoodsLocalServiceWrapper implements GoodsLocalService,
	ServiceWrapper<GoodsLocalService> {
	public GoodsLocalServiceWrapper(GoodsLocalService goodsLocalService) {
		_goodsLocalService = goodsLocalService;
	}

	/**
	* Method to persist goods object
	*
	* @param goodsobj
	* @return
	*/
	@Override
	public com.inexture.csvimporter.model.Goods add(
		com.inexture.csvimporter.model.Goods goodsobj) {
		return _goodsLocalService.add(goodsobj);
	}

	/**
	* Adds the goods to the database. Also notifies the appropriate model listeners.
	*
	* @param goods the goods
	* @return the goods that was added
	*/
	@Override
	public com.inexture.csvimporter.model.Goods addGoods(
		com.inexture.csvimporter.model.Goods goods) {
		return _goodsLocalService.addGoods(goods);
	}

	/**
	* Creates a new goods with the primary key. Does not add the goods to the database.
	*
	* @param goodsId the primary key for the new goods
	* @return the new goods
	*/
	@Override
	public com.inexture.csvimporter.model.Goods createGoods(long goodsId) {
		return _goodsLocalService.createGoods(goodsId);
	}

	/**
	* Deletes the goods from the database. Also notifies the appropriate model listeners.
	*
	* @param goods the goods
	* @return the goods that was removed
	*/
	@Override
	public com.inexture.csvimporter.model.Goods deleteGoods(
		com.inexture.csvimporter.model.Goods goods) {
		return _goodsLocalService.deleteGoods(goods);
	}

	/**
	* Deletes the goods with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param goodsId the primary key of the goods
	* @return the goods that was removed
	* @throws PortalException if a goods with the primary key could not be found
	*/
	@Override
	public com.inexture.csvimporter.model.Goods deleteGoods(long goodsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _goodsLocalService.deleteGoods(goodsId);
	}

	@Override
	public com.inexture.csvimporter.model.Goods fetchGoods(long goodsId) {
		return _goodsLocalService.fetchGoods(goodsId);
	}

	/**
	* Returns the goods with the primary key.
	*
	* @param goodsId the primary key of the goods
	* @return the goods
	* @throws PortalException if a goods with the primary key could not be found
	*/
	@Override
	public com.inexture.csvimporter.model.Goods getGoods(long goodsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _goodsLocalService.getGoods(goodsId);
	}

	/**
	* Updates the goods in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param goods the goods
	* @return the goods that was updated
	*/
	@Override
	public com.inexture.csvimporter.model.Goods updateGoods(
		com.inexture.csvimporter.model.Goods goods) {
		return _goodsLocalService.updateGoods(goods);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _goodsLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _goodsLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _goodsLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _goodsLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _goodsLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of goodses.
	*
	* @return the number of goodses
	*/
	@Override
	public int getGoodsesCount() {
		return _goodsLocalService.getGoodsesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _goodsLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _goodsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.inexture.csvimporter.model.impl.GoodsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _goodsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.inexture.csvimporter.model.impl.GoodsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _goodsLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the goodses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.inexture.csvimporter.model.impl.GoodsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of goodses
	* @param end the upper bound of the range of goodses (not inclusive)
	* @return the range of goodses
	*/
	@Override
	public java.util.List<com.inexture.csvimporter.model.Goods> getGoodses(
		int start, int end) {
		return _goodsLocalService.getGoodses(start, end);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _goodsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _goodsLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public GoodsLocalService getWrappedService() {
		return _goodsLocalService;
	}

	@Override
	public void setWrappedService(GoodsLocalService goodsLocalService) {
		_goodsLocalService = goodsLocalService;
	}

	private GoodsLocalService _goodsLocalService;
}
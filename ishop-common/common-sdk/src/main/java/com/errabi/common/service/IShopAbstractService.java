package com.errabi.common.service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class IShopAbstractService<T> {

    protected abstract void validateBusinessData(T t);

}

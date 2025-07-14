package com.skcc.oversea.framework.business.delegate;

import com.skcc.oversea.framework.exception.BizDelegateException;

public interface IBizDelegate {
    void initialize() throws BizDelegateException;

    void cleanup() throws BizDelegateException;
}
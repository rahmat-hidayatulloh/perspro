package id.co.perspro.possys.loginservice.common.service;

import id.co.perspro.possys.loginservice.common.base.BaseRequest;
import id.co.perspro.possys.loginservice.common.base.BaseResponse;

public interface BaseService<T extends BaseRequest, V extends BaseResponse> {
  V execute(T input);
}

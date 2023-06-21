package common.service;

import common.base.BaseRequest;
import common.base.BaseResponse;

public interface BaseService<T extends BaseRequest, V extends BaseResponse> {
  V execute(T input);
}

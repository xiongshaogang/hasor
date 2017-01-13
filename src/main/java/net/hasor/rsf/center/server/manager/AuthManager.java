/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.rsf.center.server.manager;
import net.hasor.core.Inject;
import net.hasor.core.Singleton;
import net.hasor.rsf.InterAddress;
import net.hasor.rsf.TraceUtil;
import net.hasor.rsf.center.server.AuthQuery;
import net.hasor.rsf.center.server.domain.AuthInfo;
import net.hasor.rsf.center.server.domain.Result;
import net.hasor.rsf.center.server.domain.ResultDO;
import net.hasor.rsf.center.server.domain.RsfCenterSettings;
import net.hasor.rsf.center.server.utils.DateCenterUtils;
import org.more.bizcommon.log.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 权限认证
 * @version : 2016年2月22日
 * @author 赵永春(zyc@hasor.net)
 */
@Singleton
public class AuthManager {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private RsfCenterSettings rsfCenterCfg;
    @Inject
    private AuthQuery         authQuery;
    //
    public Result<Boolean> checkAuth(AuthInfo authInfo, InterAddress remoteAddress) {
        LogUtils logUtils = LogUtils.create("INFO_200_00002")//
                .addLog("traceID", TraceUtil.getTraceID())//
                .addLog("appCode", authInfo.getAppKey())//
                .addLog("authCode", authInfo.getAppKeySecret())//
                .addLog("remoteAddress", remoteAddress.toHostSchema());
        //
        //
        Result<Boolean> checkResult = authQuery.checkKeySecret(authInfo, remoteAddress);
        if (checkResult == null || !checkResult.isSuccess() || checkResult.getResult() == null) {
            logger.error(logUtils.addLog("result", "failed.").toJson());
            return DateCenterUtils.buildFailedResult(checkResult);
        }
        //
        //
        logger.info(logUtils.addLog("result", checkResult.getResult()).toJson());
        ResultDO<Boolean> result = new ResultDO<Boolean>();
        result.setSuccess(true);
        result.setResult(checkResult.getResult());
        return result;
    }
}
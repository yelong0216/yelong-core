/**
 * 
 */
package org.yelong.core.model.file;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.io.FileUtilsE;
import org.yelong.core.interceptor.Interceptor;
import org.yelong.core.interceptor.Intercepts;
import org.yelong.core.interceptor.Invocation;
import org.yelong.core.interceptor.Signature;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.model.service.ModelService;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.model.sql.SqlModel;

/**
 * @since
 */
@Intercepts({
		@Signature(type = ModelService.class, method = "removeBySqlFragment", args = { Class.class,
				ConditionSqlFragment.class }),
		@Signature(type = SqlModelService.class, method = "removeBySqlModel", args = { Class.class, SqlModel.class }) })
public class RemoveFileModelInterceptor implements Interceptor {

	public static final String removeBySqlFragmentMethodName = "removeBySqlFragment";

	public static final String removeBySqlModelMethodName = "removeBySqlFragment";

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		Class<?> modelClass = (Class<?>) args[0];
		if (!FileModelable.class.isAssignableFrom(modelClass)) {
			return invocation.proceed();
		}
		if (!FileModels.isDeleteFile()) {
			return invocation.proceed();
		}
		Method method = invocation.getMethod();
		String methodName = method.getName();
		Class<FileModelable> fileModelClass = (Class<FileModelable>) modelClass;
		List<FileModelable> fileModels;
		if (methodName.equals(removeBySqlFragmentMethodName)) {
			ConditionSqlFragment conditionSqlFragment = (ConditionSqlFragment) args[1];
			fileModels = ((ModelService) invocation.getTarget()).findBySqlFragment(fileModelClass, conditionSqlFragment,
					null);
		} else if (methodName.equals(removeBySqlModelMethodName)) {
			SqlModel<?> sqlModel = (SqlModel<?>) args[1];
			fileModels = ((SqlModelService) invocation.getTarget()).findBySqlModel(fileModelClass, sqlModel);
		} else {
			throw new UnsupportedOperationException();
		}
		if (CollectionUtils.isEmpty(fileModels)) {
			return invocation.proceed();
		}
		for (FileModelable fileModelable : fileModels) {
			String filePath = fileModelable.getFilePath();
			if (StringUtils.isBlank(filePath)) {
				continue;
			}
			FileUtilsE.deleteQuietly(FileUtilsE.getFile(filePath));
		}
		return invocation.proceed();
	}

}

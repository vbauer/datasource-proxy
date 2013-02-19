package net.ttddyy.dsproxy.proxy;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.transform.QueryTransformer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Proxy InvocationHandler for {@link java.sql.Connection}.
 *
 * @author Tadaya Tsuyukubo
 */
public class ConnectionInvocationHandler implements InvocationHandler {

    private ConnectionProxyLogic delegate;

    public ConnectionInvocationHandler(Connection connection) {
        final InterceptorHolder interceptorHolder = new InterceptorHolder(QueryExecutionListener.DEFAULT, QueryTransformer.DEFAULT);
        this.delegate = new ConnectionProxyLogic(connection, interceptorHolder, "", JdbcProxyFactory.DEFAULT);
    }

    @Deprecated
    public ConnectionInvocationHandler(Connection connection, QueryExecutionListener listener) {
        final InterceptorHolder interceptorHolder = new InterceptorHolder(listener, QueryTransformer.DEFAULT);
        this.delegate = new ConnectionProxyLogic(connection, interceptorHolder, "", JdbcProxyFactory.DEFAULT);
    }

    @Deprecated
    public ConnectionInvocationHandler(
            Connection connection, QueryExecutionListener listener, String dataSourceName, JdbcProxyFactory jdbcProxyFactory) {
        final InterceptorHolder interceptorHolder = new InterceptorHolder(listener, QueryTransformer.DEFAULT);
        this.delegate = new ConnectionProxyLogic(connection, interceptorHolder, dataSourceName, jdbcProxyFactory);
    }

    public ConnectionInvocationHandler(
            Connection connection, InterceptorHolder interceptorHolder, String dataSourceName, JdbcProxyFactory jdbcProxyFactory) {
        this.delegate = new ConnectionProxyLogic(connection, interceptorHolder, dataSourceName, jdbcProxyFactory);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return delegate.invoke(proxy, method, args);
    }

}

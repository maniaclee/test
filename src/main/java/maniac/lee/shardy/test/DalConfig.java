package maniac.lee.shardy.test;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import maniac.lee.shardy.config.ShardResult;
import maniac.lee.shardy.config.TableConfig;
import maniac.lee.shardy.config.builder.SlaveConfigBuilder;
import maniac.lee.shardy.config.builder.TableConfigBuilder;
import maniac.lee.shardy.config.strategy.BucketArrayShardStrategy;
import maniac.lee.shardy.datasource.DbShardFactory;
import maniac.lee.shardy.datasource.DynamicDataSource;
import maniac.lee.shardy.spring.ShardInterceptorFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by peng on 16/2/2.
 */
@Configuration
@MapperScan("maniac.lee.shardy.test.dal.mapper")
@EnableTransactionManagement
@PropertySource("shardy/application.properties")
public class DalConfig {

    @Value(value = "classpath:shardy/sqlmap/*.xml")
    Resource[] resources;

    @Bean
    public TableConfig User() {
        return TableConfigBuilder.instance()
                .table("User")
                .masterColumn("id")
                .shardStrategy(new BucketArrayShardStrategy(0, new long[]{10000000, 10000000}, true))
                .slaveConfigs(Lists.newArrayList(
                        SlaveConfigBuilder.instance()
                                .setSlaveColumn("name")
                                .setSlaveToTableMapping(context -> {
                                    String name = context.getColumnValue().toString();
                                    String table = context.getTable();
                                    return new ShardResult(name.startsWith("shard") ? (table += "_0") : table);
                                })
                                .build()))
                .build();
    }

    @Bean
    public AbstractRoutingDataSource dynamicDataSource(DataSource user, DataSource user_shard) {
        return DynamicDataSource.instance(user, ImmutableMap.of("user_shard", user_shard));
    }

    @Bean
    public DefaultPointcutAdvisor dbShard() {
        return DbShardFactory.createDbShardInterceptor("execution(* maniac.lee.shardy.test.dal.mapper..*.*(..))");
    }

    @Bean
    public ShardInterceptorFactoryBean shardInterceptor() {
        return new ShardInterceptorFactoryBean();
    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(Interceptor shardInterceptor, AbstractRoutingDataSource dynamicDataSource) {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setTypeAliasesPackage("psyco.test.dal.entity");
        ssfb.setPlugins(new Interceptor[]{shardInterceptor});
        ssfb.setDataSource(dynamicDataSource);
        ssfb.setMapperLocations(resources);
        return ssfb;
    }

    @Bean(initMethod = "init", destroyMethod = "close", name = "user")
    public DataSource user(@Value("${user.jdbc.url}") String url,
                           @Value("${user.jdbc.user}") String username,
                           @Value("${user.jdbc.password}") String password) throws SQLException {
        return createDataSource(url, username, password);
    }

    @Bean(initMethod = "init", destroyMethod = "close", name = "user_shard")
    public DataSource user_shard(@Value("${user2.jdbc.url}") String url,
                                 @Value("${user2.jdbc.user}") String username,
                                 @Value("${user2.jdbc.password}") String password) throws SQLException {
        return createDataSource(url, username, password);
    }


    DruidDataSource createDataSource(String url, String username, String password) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxActive(60);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(60000);//60s
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(3000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        //        druidDataSource.setFilters("psyco/user/center/config");
        Properties properties = new Properties();
        properties.put("config.decrypt", "true");
        druidDataSource.setConnectProperties(properties);

        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(10000);//10s。。慢
        statFilter.setMergeSql(true);
        statFilter.setLogSlowSql(true);

        List<Filter> filterList = new ArrayList<Filter>();
        filterList.add(statFilter);
        druidDataSource.setProxyFilters(filterList);

        return druidDataSource;
    }


}

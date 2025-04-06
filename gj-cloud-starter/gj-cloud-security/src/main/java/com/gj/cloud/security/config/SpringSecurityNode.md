## 
1、第一个核心类:SecurityBuilder
2、第二个核心类:HttpSecurity
3、第三个核心类:WebSecurity
4、第四个核心类:SecurityFilterChain
5、第五个核心类:FilterChainProxy

调用执行流程：
FilterChainProxy -> doFilter() -> doFilterInternal() -> getFilters() -> 
再通过获取到的 filters 构建 VirtualFilterChain 对象，
然后使用 VirtualFilterChain 的 doFilter 链式调用执行所有的 filter

## 
HttpSecurity 在它里面构造DefaultSecurityFilterChain的默认安全过滤链
SecurityConfigurer  用来配置安全构造器。SecurityConfigurer 配置 AbstractConfiguredSecurityBuilder
AbstractConfiguredSecurityBuilder 构造安全配置

##
Authentication 认证信息，比如用户名密码等
AuthenticationManager 认证管理
AuthenticationManagerBuilder 构造认证管理器
ProviderManager 通过AuthenticationManagerBuilder构造出来的实际是它
AuthenticationProvider 被ProviderManager维护，所有提供者都用它来处理

## 一个请求是如何进入到 Spring Security 中去的呢？
DelegatingFilterProxy 这个类就是进入spring security 的开始，它位于 spring web 中.
并且它(DelegatingFilterProxy)会被注册到 tomcat 容器中去，在请求访问到tomcat中时，再通过它进入到 spring security
执行流程：在servlet执行中，进入到 spring mvc 之前，会先执行一系列的filter，此时将执行DelegatingFilterProxy
然后DelegatingFilterProxy会代理FilterChainProxy,其它的所有过滤器会由FilterChainProxy 代理。List<SecurityFilterChain> filterChains
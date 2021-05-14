该项目为使用API方式访问的授权码模式和密码模式的OAuth2代码

### 授权码模式

1. 浏览器获取授权码 访问 

```http request
GET http://localhost:9000/oauth/authorize?response_type=code&client_id=test&redirect_uri=http://mrbird.cc&scope=all&state=hello
```

- response_type:code 固定值，授权类型，标识授权码模式
- client_id:服务提供方发放的客户端id，服务端同时还存有client_secret
- redirect_uri: 重定向地址，接口操作完成后重定向的位置，该值也在服务提供方有定义
- scope: 授权资源范围
- state: 客户端状态，任意值，认证服务器会返回原值

登录后,在页面点击Approve单选,并点击Authorize按钮，会重定向到https://mrbird.cc/?code=sS4tSY&state=hello的地址

其中code属性为授权码，申请token令牌时需要

2. token获取

```http request
POST localhost:9000/oauth/token?grant_type=authorization_code&code=gn3znn&client_id=test&redirect_uri=http://mrbird.cc&scope=all
Authorization: Basic dGVzdDp0ZXN0MTIzNA==
```

- grant_type:授权类型,authorization_code表示授权码模式
- code: 授权码，上一步返回的code值
- client_id: 客户端id
- redirect_uri: 重定向地址
- scope: 授权资源范围

Authorization请求头的值为 Basic 加上`client_id:client_secret`的base64编码值

返回结果如下

```json
{
    "access_token": "a712dfcc-6bf9-4f6d-afeb-a22e50118830",
    "token_type": "bearer",
    "refresh_token": "2c83514b-85d5-46a8-a56c-22b715602af8",
    "expires_in": 43199,
    "scope": "all"
}
```

### 密码模式

```http request
POST localhost:9000/oauth/token?grant_type=password&username=123456&password=123456&scope=all
Authorization: Basic dGVzdDp0ZXN0MTIzNA==
```

返回结果如下

```json
{
    "access_token": "dcefc94b-c70d-4378-8d0b-e504dde280bc",
    "token_type": "bearer",
    "refresh_token": "b7ea4885-a72f-48d3-a6f8-623c2b8485c4",
    "expires_in": 43199,
    "scope": "all"
}
```

### 资源接口请求

```http request
GET localhost:9000/index
Authorization: bearer dcefc94b-c70d-4378-8d0b-e504dde280bc
```

Authorization请求头的值为: bearer前缀加上access_token值

返回结果如下，返回结果为org.springframework.security.core.Authentication对象

```json
{
    "authorities": [
        {
            "authority": "admin"
        }
    ],
    "details": {
        "remoteAddress": "0:0:0:0:0:0:0:1",
        "sessionId": null,
        "tokenValue": "dcefc94b-c70d-4378-8d0b-e504dde280bc",
        "tokenType": "bearer",
        "decodedDetails": null
    },
    "authenticated": true,
    "userAuthentication": {
        "authorities": [
            {
                "authority": "admin"
            }
        ],
        "details": {
            "grant_type": "password",
            "username": "123456",
            "scope": "all"
        },
        "authenticated": true,
        "principal": {
            "password": null,
            "username": "123456",
            "authorities": [
                {
                    "authority": "admin"
                }
            ],
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        },
        "credentials": null,
        "name": "123456"
    },
    "oauth2Request": {
        "clientId": "test",
        "scope": [
            "all"
        ],
        "requestParameters": {
            "grant_type": "password",
            "username": "123456",
            "scope": "all"
        },
        "resourceIds": [],
        "authorities": [],
        "approved": true,
        "refresh": false,
        "redirectUri": null,
        "responseTypes": [],
        "extensions": {},
        "grantType": "password",
        "refreshTokenRequest": null
    },
    "principal": {
        "password": null,
        "username": "123456",
        "authorities": [
            {
                "authority": "admin"
            }
        ],
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true,
        "enabled": true
    },
    "credentials": "",
    "clientOnly": false,
    "name": "123456"
}
```

### 拦截放行

对于接口的拦截过滤可以再SecurityConfig的`protected void configure(HttpSecurity http)`方法内设置

# 更新说明

本次更新修复了服务层中的空指针异常，并统一了登录与注册页面的样式。

## 变更内容
- 在 `UserServiceImpl` 中注入 `TourLineService`，避免在发布或下架旅游线路时出现空指针错误。
- 新增 `auth-page` 与 `auth-container` 样式，登录与注册页面引用统一的 CSS 文件。
- 简化 `login.html` 与 `register.html`，移除内联样式，界面更加美观。

## 使用方法
依旧可使用 `mvn spring-boot:run` 启动项目（需自行提供 `pom.xml`）。

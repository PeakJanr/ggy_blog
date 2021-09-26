## Blog项目

### 框架搭建问题

1.首先导入热部署会导致yml无法自动提示其他东西，并且热部署的<scope>一定要在runtime;

### 异常处理

| &lt ;   | <    | 小于号 |
| ------- | ---- | ------ |
| &gt ;   | >    | 大于号 |
| &amp ;  | &    | 和     |
| &apos ; | '    | 单引号 |
| &quot ; | "    | 双引号 |

#### 自定义异常处理类

```java

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/4 10:21
 */
@ControllerAdvice  //拦截所有Controller抛出的异常，对异常进行统一的处理
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) //表示该方法可以处理所有异常
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        //打印异常信息
        logger.error("request URL : {} ,Exception: {}",request.getRequestURL(),e);
        
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null)
        {
            throw e;
        }
        //设置异常信息内容到页面
        ModelAndView mv =new Mode	lAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //返回到页面
        mv.setViewName("error/error");
        return mv;

    }
}
```

https://www.cnblogs.com/lenve/p/10748453.html

```java
/**
 * 404页面访问异常 对象不存在
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/4 11:08
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

（1）@ExceptionHandler(Exception.class) 中的@ExceptionHandler作用于方法，用来指明异常的类别，括号中表示异常的类别。上面的写法表示exceptionHandler方法可以处理所有的异常。
（2）@ControllerAdvice注解是Controller的加强版，可对Controller中被@RequestMapping标注的方法进行一些逻辑处理，此注解一般用于异常处理。
（3）AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)的方法的基本功能：在e中查找ResponseStatus的标志，若不存在直接返回 null, 否则返回该标志信息。

要能在error.html打印错误信息，需要加上这几行代码

```html
<h1>错误</h1>
<div>
    <div th:utext="'&lt;!--;'" th:remove="tag"></div>
    <div th:utext="'Failed Request URL :'+${url}" th:remove="tag"></div>
    <div th:utext="'Exception message' + ${exception.message}"  th:remove="tag"></div>
    <ul th:remove="tag">
        <li th:each="st:${exception.stackTrace}" th:remove="tag"><span th:utext="${st}" th:remove="tag"></span></li>
    </ul>
    <div th:utext="'--&gt;'" th:remove="tag"></div>
</div>

</body>
```

### 日志处理

```java
/**
 * 日志处理
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/4 15:10
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.peak.web..*.*(..))")
    public void log()
    {


    }

    /**
     * 记录每一次访问的url,ip地址，执行的方法和对应的参数列表
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }
    @After("log()")
    public void doAfter()
    {
        logger.info("-----after----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result)
    {
        logger.info("result+{}"+result);
    }

    @Data
    @AllArgsConstructor
    private class RequestLog
    {
        private String url;   //请求url
        private String ip;    //访问者的ip
        private String classMethod; //调用的方法Classmethod
        private Object[] args;   //传过来的参数

    }

}
```

### 博客数据库设计

主要类别：

blog 博客类

Type:类型

Tag:标签

Comment:评论

User：用户

![image-20210905085725691](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210905085725691.png)



Comment 具有自关联的关系

![image-20210905085942645](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210905085942645.png)

Blog中的属性

![image-20210905091732960](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210905091732960.png)

### 登录功能实现

```java
@PostMapping("/login")
public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes)
{
    User user =userService.cheakUser(username,password);
    if(user != null)
    {
        user.setPassword(null);
        session.setAttribute("user",user);
        return "admin/index";
    }else
    {
        attributes.addFlashAttribute("msg","用户名和密码错误");
        return "redirect:/admin";
    }
}
```

```java
/**
 * 配置拦截器
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 9:36
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;

    }
}
```

配置拦截器

```java
import com.peak.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {   //配置拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
```

### 注销功能

```java
@GetMapping("/logout")
public String logout(HttpSession session )
{
    session.removeAttribute("user");
    return "redirect:/admin";
}
```

### 分类

####  分页查询

```java
@GetMapping("/types")
public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
    PageHelper.startPage(pagenum, 5);
    List<Type> allType = typeService.getAllType();
    //得到分页结果对象
    PageInfo<Type> pageInfo = new PageInfo<>(allType);
    model.addAttribute("pageInfo", pageInfo);
    return "admin/types";
}
```



####　增加

```java
@PostMapping("/types/insert")
public String addType(Type type, RedirectAttributes attributes)
{
    try {
        typeService.saveType(type);
        attributes.addFlashAttribute("msg","添加成功");
    }catch (Exception e)
    {
        attributes.addFlashAttribute("msg","添加失败");
        return "redirect:/admin/types/input";
    }
    return "redirect:/admin/types";
}
```

#### 删除

```java
@GetMapping("/types/delete/{id}")
public String deleteType(@PathVariable("id") Long id,RedirectAttributes attributes) {
    try {
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/types";
    } catch (Exception e) {
        attributes.addFlashAttribute("msg", "删除失败");
        return "redirect:/admin/types";
    }
}
```

#### 修改

```java
@PostMapping("/types/update/{id}")
public String updateType(@PathVariable("id") Long id, Type type,RedirectAttributes attributes)
{
        type.setId(id);
    try {
        typeService.updateType(type);
        attributes.addFlashAttribute("msg","更新成功");
        return "redirect:/admin/types";
    }catch (Exception e)
    {
        attributes.addFlashAttribute("msg","更新失败");
        return "redirect:/admin/types";
    }
}
```

## 博客详情

####　分頁查询:

需要的博客信息 id,title,type,published,updateTime字段

```java
@GetMapping("/blogs")
public String toBlog(@RequestParam(required = false,defaultValue = "1",value = "pagenum") int pagenum, Model model)
{
    PageHelper.startPage(pagenum,5);
    List<Blog> blogs = blogService.getAllBlog();
    PageInfo pageInfo = new PageInfo(blogs);
    model.addAttribute("pageInfo",pageInfo);
    setTypeAndTag(model);  //查询类型和标签
    return "/admin/blogs";
}
```



```xml
<select id="getAllBlog" resultMap="blog">
  SELECT
    b.id,b.title,b.flag,b.update_time,b.type_id,b.published
       ,t.name type_name
  FROM t_blog b,t_type t
  WHERE b.type_id = t.id
</select>
```

#### 模糊查询

传过来字符串，根据字符串模糊查询

```java
/**
 * 条件查询
 * @param blog
 * @param pagenum
 * @param model
 * @return
 */
@PostMapping("/blogs/search")
public String SearchBlogs(Blog blog,@RequestParam(required = false,defaultValue = "1", value = "pagenum") int pagenum,Model model)
{
    PageHelper.startPage(pagenum,5);
    List<Blog> blogs = blogService.searchAllBlogs(blog);
    PageInfo pageInfo =new PageInfo(blogs);
    model.addAttribute("pageInfo",pageInfo);
    setTypeAndTag(model);
    return "/admin/blogs";
}
```

```xml
<select id="searchAllBlogs" resultMap="blog">
  <bind name="pattern" value="'%' + title + '%'" />    /*模糊查询*/
  select b.id, b.title, b.update_time,  b.published, b.type_id, t.id, t.name
  from t_blog b ,t_type t
  <where>
    <if test="1 == 1">
      b.type_id = t.id    /*博客类型id=类型id*/
    </if>
    <if test="typeId != null">
      and b.type_id = #{typeId}       /*根据博客类型查询*/
    </if>
    <if test="title != null">
      and b.title like #{pattern}   /*根据博客title模糊查询*/
    </if>
  </where>
</select>
```

#### 增加

跳转

```java
@GetMapping("/blogs/input")
public String Toaddblog(Model model)
{
    model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
    setTypeAndTag(model);
    return "/admin/blogs-input";
}
```



根据是否有id 确定是修改还是增加

增加需要的字段有 title,content,first_picture,views,apperation,share_statement,commentalbled,published,create_time,update_time,type_id,user_id,tag_ids,description

```java

@PostMapping("/blogs/save")
public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes)
{
    blog.setType(typeService.getType(blog.getType().getId()));
    blog.setUser((User) session.getAttribute("user"));
    blog.setTags(tagsService.getTagByString(blog.getTagIds()));
    System.out.println(blog);
    if (blog.getId() == null) {   //id为空，则为新增
        blogService.saveBlog(blog);
        attributes.addFlashAttribute("msg","新增成功");
    } else {
        blogService.updateBlog(blog);
        attributes.addFlashAttribute("msg","修改成功");
    }
    return "redirect:/admin/blogs";
}
```

增加的话 需要得到user_id 可以通过serssion得到User对象,需要处理中间表

```java
@Override
public void saveBlog(Blog blog) {
    blog.setCreateTime(new Date());
    blog.setUpdateTime(new Date());
    blog.setViews(0);
    blogDao.insert(blog);
    Long blogId = blog.getId();
    List<Tag> tags = blog.getTags();
    BlogAndTag blogAndTag =null;
    for (Tag tag: tags) {
        blogAndTag = new BlogAndTag(blogId,tag.getId());
        blogDao.saveBlogAndTag(blogAndTag);
    }
}

```

#### 编辑

回显

```java
@GetMapping("/blogs/update/{id}")
public String toUpdate(@PathVariable("id") Long id,Model model)
{
    setTypeAndTag(model);
    Blog blog =blogService.getBlogByid(id);
    model.addAttribute("blog",blog);
    return "/admin/blogs-input";
}
```

```java
@Override
public void updateBlog(Blog blog) {
    blog.setUpdateTime(new Date());
    //将标签的数据存到t_blogs_tag表中
    List<Tag> tags = blog.getTags();
    //刪除中間表關係
    blogDao.deleteBlogAndTag(blog.getId());
    BlogAndTag blogAndTag = null;
    for (Tag tag : tags) {
        blogAndTag = new BlogAndTag(blog.getId(), tag.getId());
        blogDao.saveBlogAndTag(blogAndTag);
    }
    blogDao.updateBlog(blog);
}
```

```xml
 <insert id="saveBlogAndTag"  useGeneratedKeys="true">
    insert into t_blog_tags (blog_id,tag_id) values (#{blogId},#{tagId})
  </insert>

<delete id="deleteBlogAndTag">
  delete from t_blog_tags where blog_id = #{id}
</delete>
<insert id="insert" keyColumn="id" keyProperty="id" parameterType="Blog" useGeneratedKeys="true">
  insert into t_blog (title,content,first_picture,flag,views,apperation,share_statement,commentalbled
  ,published,create_time,update_time,type_id,user_id,tag_ids,description)
  values (#{title},#{content},#{firstPicture},#{flag},#{views},#{apperation},#{shareStatement},#{commentalbled}
  ,#{published},#{createTime},#{updateTime},#{type.id},#{user.id},#{tagIds},#{description})
</insert>


<update id="updateBlog" parameterType="com.peak.pojo.Blog">
  update t_blog
  set title = #{title},content =#{content},first_picture = #{firstPicture},flag = #{flag},
      apperation=#{apperation},share_statement = #{shareStatement},
      published=#{published},update_time=#{updateTime},type_id=#{type.id},user_id=#{user.id},
      tag_ids=#{tagIds},commentalbled=#{commentalbled},description=#{description}
  where id = #{id}
</update>
```

删除

```java
@GetMapping("/blogs/delete/{id}")
public String deleteBlog(@PathVariable("id") Long id,RedirectAttributes attributes)
{
    try {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg","刪除成功");
    }catch (Exception e)
    {
        attributes.addFlashAttribute("msg","刪除失敗");
    }
    return "redirect:/admin/blogs";
}
```


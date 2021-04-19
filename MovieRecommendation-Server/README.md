# 技术栈说明
- 数据库：MySQL、MongoDB
- 持久层：MyBatis
- 开发框架：SpringBoot
- 缓存：Redis
- 搜索引擎：ElasticSearch
- 消息队列：Kafka
- 计算框架：Spark （core、SQL、streaming）
- 静态资源访问（图片、视频、文件等）：Nginx
- 日志：Logback
- 单元测试：JUnit
- 插件：Swagger、Lombok、PageHelper、FastJSON

简化开发说明：

为减少开发工作量，部分实体类、service及mapper使用IDEA的插件EasyCode插件生成，在此基础上进行业务逻辑修改。

---

# 项目结构说明
- businessServer：接口子项目
  - config：配置类
  - controller：控制层
  - core
    - constants：常量类
    - model：统一返回视图类
    - utils：工具类
  - dao：dao层，映射mapper
  - entity：实体类
  - es：ElasticSearch配置及工具类
  - interceptor：拦截器
  - service：业务逻辑层
  - mapper：SQL语句
- recommender：各类推荐算法子项目
  - contentRecommender：基于内容TF-IDF推荐
  - statisticRecommender：统计推荐
  - offlineRecommender：离线推荐
  - steamingRecommender：实时推荐

------



# 业务逻辑说明

## 后端

- ElasticSearch作为电影的搜索引擎，全文搜索速度比访问MySQL快很多；

- 热点电影数据会保存在Redis中，同时用户的近期评论数据也会在Redis中，便于后面实时评分的计算推荐；

- 所有的原始数据都保存在MySQL中，推荐算法生成的结果集均保存至MongoDB中，便于获取及二次计算；

## 算法

**这部分有一些数学公式无法正常显示，建议下载后使用Typora即可正常显示，也可安装Chrome插件MathJax Plugin for Github（显示会有些许错误）。**

统计推荐、内容推荐、离线推荐算法有服务器调度框架按时调度；实时推荐算法会一直运行

- 统计推荐
  - 历史榜单：通过评分总数排序
  - 近期热门榜单：通过近期月份及评分总数排序
  - 各类别榜单：通过各分类电影的评分分数排序
- 基于内容推荐

  - TF-IDF（词频-逆向文件频率）算法推荐：

    用户对电影会有一些标签，将这些标签提取，并作为内容特征向量，同时通过TF-IDF算法对标签的权重进行调整，从而尽可能地接近用户偏好。
- 离线推荐
  - ALS（最小二乘法）隐语义模型算法推荐：
  
    将用户的评分数据随机切分为训练集和测试集，使用ALS算法训练模型，并进行参数调优获取RSME均方根误差最小的参数，最终得到最合适的模型。
  
    使用Spark的recommendProductsForUsers方法生成用户推荐结果集。
  
    该算法最终会为用户、电影分别生成最终的特征矩阵，分别是表示用户特征矩阵的U(m x k)矩阵，每个用户由 k个特征描述；表示电影特征矩阵的V(n x k)矩阵，每个物品也由 k 个特征描述。比如V(n x k)表示电影特征矩阵，每一行是一个 k 维向量，虽然我们并不知道每一个维度的特征意义是什么，但是k 个维度的数学向量表示了该行对应电影的特征。
  
    每个电影用V(n x k)每一行的向量表示其特征：
    $$
    <t_1,t_2,t_3,\cdots,t_k>
    $$
    对于两个电影p和q，之间的相似度**Sim(p,q)**公式为：
    $$
    Sim(p,q)=\frac{\sum_{i=0}^N(t_{pi}×t_{qi})}{\sqrt{}{\sum_{i=0}^Nt_{pi}^2}×\sqrt{}{\sum_{i=0}^Nt_{qi}^2}}
    $$
    
  - ItemCF 基于物品的同现相似度推荐：
  
    根据同现相似度来计算：如果两个电影有同样的受众（感兴趣的人群），那么它们就是有内在相关性的。
    
    Ni 是评分电影 i 的用户列表，Nj 是评分电影 j 的用户列表。
    $$
    w_{ij} = \frac{|N_i ∩ N_j|}{\sqrt{}{(|N_i ||N_j |)}}
    $$
  
- 实时推荐

  - SparkStreaming + Kafa 的实时评分算法推荐：

    当用户u对电影p 进行了评分，将触发一次对u 的推荐结果的更新。

    由于用户u 对电影p 评分，对于用户u 来说，他与p 最相似的电影们之间的推荐强度将发生变化，所以选取与电影p 最相似的K 个电影作为候选电影。每个候选电影按照“推荐优先级”这一权重作为衡量这个电影被推荐给用户u 的优先级。这些电影将根据用户u 最近的若干评分计算出各自对用户u 的推荐优先级，然后与上次对用户u 的实时推荐结果的进行基于推荐优先级的合并、替换得到更新后的推荐结果。

    首先，**获取用户u 按时间顺序最近的K 个评分，记为RK；获取电影p 的最相似的K 个电影集合，记为S；**

    然后，对于每个电影q∈S，计算其推荐优先级 E_uq ，计算公式如下：
    $$
    E_{uq} = \frac{\sum_{r \in RK}sim(1,r)×R_r}{sim\_sum} + \lg{max\{incount,1\}} - \lg{max\{recount,1\}}
    $$
    R_r表示用户u 对电影r 的评分；
    
    sim(q,r)表示电影q 与电影r 的相似度，设定最小相似度为0.6，当电影q和电影r 相似度低于0.6 的阈值，则视为两者不相关并忽略；
    
    sim_sum 表示q 与RK 中电影相似度大于最小阈值的个数；
    
    incount 表示RK 中与电影q 相似的、且本身评分较高（>=3）的电影个数；
    
    recount 表示RK 中与电影q 相似的、且本身评分较低（<3）的电影个数；
    
    **公式的意义如下：**
    
    首先对于每个候选电影q，从u 最近的K 个评分中，找出与q 相似度较高（>=0.6）的u 已评分电影们，对于这些电影们中的每个电影r，将r 与q 的相似度乘以用户u 对r 的评分，将这些乘积计算平均数，作为用户u 对电影q 的评分预测即:
    $$
    E_{uq} = \frac{\sum_{r \in RK}sim(1,r)×R_r}{sim\_sum}
    $$
    然后，将u 最近的K 个评分中与电影q 相似的、且本身评分较高（>=3）的电影个数记为 incount，计算lgmax{incount,1}作为电影 q 的“增强因子”，意义在于电影q 与u 的最近K 个评分中的n 个高评分(>=3)电影相似，则电影q 的优先级被增加lgmax{incount,1}。如果电影 q 与 u 的最近 K 个评分中相似的高评分电影越多，也就是说n 越大，则电影q 更应该被推荐，所以推荐优先级被增强的幅度较大；如果电影q 与u 的最近K 个评分中相似的高评分电影越少，也就是n 越小，则推荐优先级被增强的幅度较小；
    
    而后，将u 最近的K 个评分中与电影q 相似的、且本身评分较低（<3）的电影个数记为 recount，计算lgmax{recount,1}作为电影 q 的“削弱因子”，意义在于电影q 与u 的最近K 个评分中的n 个低评分(<3)电影相似，则电影q 的优先级被削减lgmax{incount,1}。如果电影 q 与 u 的最近 K 个评分中相似的低评分电影越多，也就是说n 越大，则电影q 更不应该被推荐，所以推荐优先级被减弱的幅度较大；如果电影q 与u 的最近K 个评分中相似的低评分电影越少，也就是n 越小，则推荐优先级被减弱的幅度较小；
    
    最后，将增强因子增加到上述的预测评分中，并减去削弱因子，得到最终的q 电影对于u 的推荐优先级。在计算完每个候选电影q 的推荐优先级E_uq后，将生成一组<电影q 的ID, q 的推荐优先级>的列表updatedList：
    $$
    updateList = \cup_{q \in S}\{q_{id},E_{uq}\}
    $$
    而在本次为用户u 实时推荐之前的上一次实时推荐结果Rec 也是一组<电影m,m 的推荐优先级>的列表，其大小也为K：
    $$
    Rec = \cup_{m \in Rec}\{m_{id},E_{um}\},len(Rec)=K
    $$
    接下来，将updatedList与本次为u 实时推荐之前的上一次实时推荐结果Rec进行基于合并、替换形成新的推荐结果NewRec：
    $$
    NewRec = topK(i \in Rec \cup updateList ,cmp=E_{ui})
    $$
    其中，i表示updatedList与Rec 的电影集合中的每个电影，topK 是一个函数，表示从并集中选择出最大的 K 个电影，cmp 表示topK 函数将推荐优先级值最大的K 个电影选出来。最终，NewRec 即为经过用户u 对电影p 评分后触发的实时推荐得到的最新推荐结果。
    
    总之，实时推荐算法流程流程基本如下：
    
    （1）用户u 对电影p 进行了评分，触发了实时推荐的一次计算；
    
    （2）选出电影p 最相似的K 个电影作为集合S；
    
    （3）获取用户u 最近时间内的K 条评分，包含本次评分，作为集合RK；
    
    （4）计算电影的推荐优先级，产生<qID,>集合updatedList；
    
    （5）与之前的Rec合并，按照推荐优先级从大到小取K个作为最终推荐结果。

---

# 配置说明
所有配置文件全部位于src/main/resources目录下
- application.yml：项目全局配置文件
- logback.xml：日志配置配置

---

# 日志说明

日志采用springboot推荐的logback框架，对应src/main/resources/logback.xml配置文件。

拥有两个Appender，分别是development（日志输出到控制台）和production（日志输出将产生文件并存放至log目录下）。

Appender跟随项目环境自动切换。

---

# 切换环境
修改application.yml下的project.env即可，取值为**production**和**development.**
同时这也是logback appender的名称，环境切换后将自动采用对应的appender。如需增加环境配置，只需要添加对应的logback appender即可。

---

# 单元测试
#### 单元测试基类 
BaseTest：位于test目录下的com.pcy.movierecommendation包
#### 编写单元测试
编写单元测试类，继承BaseTest即可，测试结果通过BaseTest基类中的print成员方法输出。

---

# Swagger接口文档说明
#### Swagger地址
http://localhost:8090/swagger-ui.html

#### Swagger的启用和禁用
在application.yml中修改swagger.enabled即可，true表示启用Swagger，false表示禁用Swagger。在项目启动后可查看Swagger的状态。

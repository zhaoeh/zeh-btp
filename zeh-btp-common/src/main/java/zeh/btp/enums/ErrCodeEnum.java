//package zeh.btp.enums;
//
//
//
//import java.text.MessageFormat;
//
///**
// * 错误码枚举
// */
//public enum ErrCodeEnum implements ResultCode {
//
//    MSG_000000,//000000^The Request is too frequent!^请求过于频繁！
//    MSG_100000,//100000^wrong infProductId or infPwd!^产品ID或产品密码错误!
//    MSG_100001,//100001^wrong infFlag!^接口功能标识错误!
//    MSG_100002,//100002^unknown Exception!^未知异常!
//    MSG_100003,//100003^The maxium number of per page is 5000!^每页最多显示 5000行!
//    MSG_100004,//100004^The maxium number of per query is 300!^每次查询最多300条!
//    MSG_100005,//100005^Sensitive info encryption fail!^敏感信息加密失败!
//    MSG_100006,//100006^Sensitive info decryption fail!^敏感信息解密失败!
//    MSG_100007,//100006^operation exception!^操作异常!
//    MSG_100008,//100008^The maximum number of per page is 5000!^每页最多显示5000行!
//    MSG_100020,//100020^unknown Exception!^未知异常!
//    MSG_304921,//304921^Repeat apply!^在短时间内重覆提交两次洗码申请!
//    MSG_300004,//300004^Customer Id should not be empty!^用户ID不得为空值
//    MSG_300005,//300005^Status should not be empty!^状态不得为空值
//    MSG_300011,//300011^Product Id should not be empty!^产品ID不得为空值
//    MSG_300012,//300012^LoginName should not be empty!^登录名不得为空值
//    MSG_300013,//300013^Verify Time should not be empty!^有效时间（分）不得为空值
//    MSG_300014,//300014^Verify Code should not be empty!^验证码不得为空值
//    MSG_300015,//300015^Verify Code Max Count should not be empty!^可验证次数不得为空值
//    MSG_300016,//300016^Verify Function should not be empty!^验证方式不得为空值
//    MSG_300017,//300017^Verify Type should not be empty or null check for activity_id if type is 12!^验证用途不得为空值或者不匹配活动检查逻辑
//    MSG_300018,//300018^Verify Source should not be empty!^用户来源不得为空值
//    MSG_300019,//300019^Created By should not be empty!^创建人员不得为空值
//    MSG_300020,//300020^No Available Verify Code^没有正在等待确认中的验证码
//    MSG_300021,//300021^Verification code expired^验证码超时
//    MSG_300022,//300022^Over Count^验证码输入超过验证次数
//    MSG_300023,//300023^Bond Context should not be empty!^绑定内容不得为空值
//    MSG_300024,//300024^Bond Type should not be empty!^绑定类别不得为空值
//    MSG_300025,//300025^Verify Code Id should not be empty!^验证码流水号不得为空值
//    MSG_300026,//300026^Bond Old Context should not be empty!^旧绑定内容不得为空值
//    MSG_300027,//300027^Wrong Verify Code^验证码输入错误
//    MSG_300029,//300029^if parameter terminal is null,then loginName can't be null^此时登录名不能为空
//    MSG_300031,//300031^Error Info Verify Code^验证码异常,请重新发送
//    MSG_300032,//300032^IP address should not be empty!^IP地址不得为空值
//    MSG_300034,//300034^End Point should not be empty!^终端类型不得为空值
//    MSG_300037,//300037^Customer sub account should not modify email!^子账号不可以修改邮箱
//    MSG_335010,//335010^Bill NO. not exist or bill status is incrrect!^单号不存在或者单据状态不对！
//    MSG_335015,//335014^Record lock fail!^锁定记录失败！
//    MSG_500000,//500000^Parameters cannot be null!^参数不能为空!
//    MSG_500001,//500001^this operation is forbidden!^操作被禁止!
//    MSG_510005,//510005^Customer does not existed !^Customer不存在!
//    MSG_820027,//820027^Job already existed!^Job 已存在!
//    MSG_820030,//820030^Too many data in one operation !^一次操作的数据太多!
//    MSG_532000,//532000^production constants not config red envelope switch!^未配置红包功能开关!
//    MSG_532001,//532001^production constants not config overdue date!^未配置过期日期!
//    MSG_532002,//532002^production constants not config max amount!^未配置红包最大金额!
//    MSG_532003,//532003^production constants not config min amount!^未配置红包最小金额!
//    MSG_532004,//532004^red envelope amount not incloud config!^红包的金额超出要求范围!
//    MSG_532005,//532005^production constants not config quantity!^未配置用户红包数量限制!
//    MSG_532006,//532006^max quantity over constants config by day!^今日发送红包数量超过限制!
//    MSG_532007,//532007^the customer be prohibit send red envelope!^用户已被禁止发红包!
//    MSG_532008,//532008^the customer already received!^用户已经领取过该红包!
//    MSG_532009,//532009^red envelope not exists!^红包信息不存在!
//    MSG_532010,//532010^red envelope had overdued!^红包已过期!
//    MSG_532011,//532011^too later, the red envelope is empty!^您来晚了，红包已经领完了!
//    MSG_532012,//532012^the red envelope had approved!^红包已审批!
//    MSG_532014,//532014^receive red envelope Failure. receivePassword error!^领取红包失败，口令错误!
//    MSG_532015,//532015^red envelope had forbiddened!^红包功能已被禁用!
//    MSG_532016,//532016^remaining balance insufficient!^扣除优惠金额：%s元之后，余额不足以发送红包!
//    MSG_532017,//532017^Customer's currency type is not the same with red envelop currency type!^会员货币类型与红包货币类型不一致!
//    MSG_534000,//534000^Have no available royal foundation balance^没有可转出的皇家会员理财利息
//    MSG_534001,//534001^Transfer amount not the same^请求转出的理财额度不一致辞
//    MSG_534002,//534002^Transfer to local fail^请求从皇家会员理财转账到本地失败
//    MSG_534004,//534004^Create transfer log fail^请求从皇家会员理财转账到本地失败,创建转账记录时异常
//    MSG_534005,//534005^Update Interest log fail^请求从皇家会员理财转账到本地失败,更新利息记录时异常
//    MSG_534006,//534006^Old flag parameter incorrect^初始状态参数不正确
//    MSG_535000,//535000^lock data failed!^锁定当前数据失败！
//    MSG_500018,//500018^Parameter can't be null or empty!^参数不能为空!
//    MSG_100019,//100019^wrong infFlag!^接口功能标识错误!
//    MSG_202127,//202127^LastUpdatedBy should not be empty,the length must be between 2 to 20!^最后修改人不能为空,长度必须在2和20之间
//    MSG_202161,//202161^LoginName should not be empty,the length must be between 2 to 20!^登录名不能为空,长度必须在2和20之间
//    MSG_403606,//403606^ProductId should not be empty!^产品ID不能为空!
//    MSG_300836,//300836^LoginName should not be empty!^登录名不能为空!
//    MSG_306203,//306203^IpAddress should not be empty,and length must for 1 to 63 between!^IP地址不能为空,长度必须在 1和 63之间
//    MSG_305210,//305210^CreatedBy does not exist.^创建人不存在
//    MSG_301513,//301513^Remarks length must be between 1 to 200!^备注长度必须在1和200之间
//    MSG_100021,//100021^wrong infProductId or infPwd!^产品ID或产品密码错误!
//    MSG_300837,//300837^Customer ID should not be empty!^客户ID不能为空!
//    MSG_304204,//304204^bankAccountNo can't repeat!^很抱歉,该银行卡已被其他账号绑定,请联系客服,谢谢!
//    MSG_304209,//304209^bankAccountNo can't repeat!^很抱歉,该USDT钱包已被其他账号绑定,请联系客服,谢谢!
//    MSG_304208,//304208^bankAccountNo can't repeat!^很抱歉,该支付宝账号已被其他账号绑定,请联系客服,谢谢!
//    MSG_304207,//304207^bankAccountNo can't repeat!^很抱歉,该支付宝二维码已被其他账号绑定,请联系客服,谢谢!
//    MSG_304206,//304206^bankAccountNo can't repeat!^很抱歉,该币付宝已被其他账号绑定,请联系客服,谢谢!
//    MSG_304205,//304205^bankAccountNo can't repeat!^很抱歉,该虚拟币地址已被其他账号绑定,请联系客服,谢谢!
//    MSG_335121,//335121^Customer does not existed^客户不存在
//    MSG_100011,//100011^CreatedBy should not be empty,and length must for 2 to 20 between!^创建人不能为空,长度必须在 2和 20之间
//    MSG_100012,//100012^promotionType can't be null^活动类型不能为空
//
//    MSG_203417,//203417^register IP wrong^注册IP解析有误
//    MSG_523149,//523149^StaticTime should not be empty^统计时间不能为空
//    MSG_304345,//304345^Could not update credit. Customer Type error!^用户类型错误!
//    MSG_202145,//202145^LastUpdatedBy not exist!^最后修改人不存在!
//    MSG_202201,//202201^Password Error!^密码错误！
//    MSG_200000,//200000^参数值不合法!^Param Value inValid!
//    MSG_201011,//201011^LoginName and customerId should not be empty together!^登录名和客户id不能同时为空!
//    MSG_304930,//304930^rebatedBetAmountTotal is greater than {0} ^分类洗码有效投注额 大于 {0}
//    MSG_304931,//304931^currentBetAmount {0} is greater than {1} ^本次申请洗码金额 {0} 大于 {1}
//    MSG_304932,//304932^rebatedBetAmountTotal {0} is greater than {1} ^已洗码总有效投注额 {0} 大于 {1}
//    MSG_304933,//304933^xmAmount {0} is greater than {1} ^洗码金额 {0} 大于 {1}
//    MSG_254707,//254707^deduct credit failed:exchange crash to game credit error^扣除额度失败：游戏币兑换现金币出现错误
//    MSG_254709,//254709^deduct credit failed:deduct game credit error^扣除额度失败：扣除游戏币出现错误
//    MSG_254710,//254710^deduct credit failed:deduct game credit no change^扣除额度失败：扣除游戏币无变化
//    MSG_301620,//301620^AMTC Agent account's(  {0}  ) balance is not sufficient!^AMTC推广账号余额不足!
//    MSG_301621,//301621^AMTC Agent account is empty!^AMTC推广账号不存在，请配置!
//    MSG_304203,//304203^WSCustomers's have unfinished same type xm!^会员有未完成的同类型洗码提案!
//    MSG_202202,//202202^ConfigurationId not exist^配置不存在
//    MSG_302910,//302910^When the loginName or requestId is empty the create date range or rebate period date range or audit date must not empty!^登录名或提案ID为空时，创建时间段、洗码周期段、审核时间段必须要存在一组值！
//    MSG_304920,//304920^WSCustomers's have {0} Promotion!同周有享受过 {0} ^优惠,无法自助提交洗码!
//    MSG_304922,//304921^WSCustomers's have {0} Promotion!首周有享受过 {0} ^优惠,无法自助提交洗码!
//    MSG_305103,//305103^request and request details amount not match.^大类洗码提案和提案对应的详情总金额不一致。
//    MSG_304942,//304942^xm betamount more than dc betamount^投注总额大于dc洗码投注总额
//    MSG_304923,//304921^Amount Abnormal^提交洗码金额可疑
//    MSG_304934,//304933^xm config not exist.^没有找到大类下的该洗码配置： xmType
//    MSG_335000,//335000^Config not exist!^配置不存在！
//    MSG_335016,//335015^The bill has been transfer out!^此订单已转出！
//    MSG_335011,//335011^Under {0} hours,no calculate the interest!^还未满 {0} 小时，不计算利息！
//    MSG_335012,//335012^Its already reached limit hours!^已达到最大计息小时数！
//    MSG_206606,//206606^code invalid^验证码失效
//    MSG_304919,//304919^WSCustomers's have Deposit first Promotion!^同周有享受过首存优惠,无法自助提交洗码!
//    MSG_206618,//206618^submit a number greater than  {0} ^提交错误次数超过  {0}  次
//    MSG_206610,//206610^Verification code error^验证码错误!
//    MSG_502721,//502721^PeriodNum is exist^期数已存在
//    MSG_502031,//502031^Increase results failed.can't adjust!^增加结果失败,无法核算
//    MSG_500224,//500224^winConsecutive data repeat!^连赢数据重复
//    MSG_335110,//335110^Record not found!^未找到记录！
//    MSG_335111,//335111^Interest logs data abnormal,stop calculate interest!^计息记录数据存在异常，停止计息！
//    MSG_335014,//335014^Under {0} hours than last time,no calculate the interest!^距离上次结息还未满 {0} 小时，不计算利息！
//    MSG_500404,//500404^That date, no configuration prizes^该日期没有配置奖品
//    MSG_335009,//335016^The bill has not been transfer out!^此订单尚未成功转出！
//    MSG_335017,//335017^The remaining interest rate of this order is 0!^此订单剩余计息次数为0！
//    MSG_500422,//500422^it must be the same quantity between the lv6 prize and the people who win the lv6 prize,6^等奖特制奖中奖会员数必须等于6等奖特制奖品数
//    MSG_500405,//500405^Special prize is not greater than the prize total^特制奖品不能大于奖品总数
//    MSG_335018,//335018^Did not get the corresponding deposit interest rate!^没有查询到对应存款的利率配置!！
//    MSG_500324,//500324^PrizeNum is must greater than 0^奖品总数必须大于 0
//    MSG_334941,//334941^Reach the limit {0} in recent days {1} !,actual increase the interest {2}^利息达到近{0}天内限额{1},实际增加利息{2}
//    MSG_334942,//334942^Reach the limit {0} in recent days {1}!,actual increase the interest {2}^利息达到近{0}天内限额{1},实际增加利息{2}
//    MSG_334943,//334943^Reach the limit {0}} in this period !,actual increase the interest {1}^利息达到当期限额{0},实际增加利息{1}
//    MSG_334944,//334944^Reach the limit {0} in this period !,actual increase the interest {1} ^利息达到当期限额{0},实际增加利息{1}
//    MSG_500406,//500406^Lv1 Special prize is not greater than the Lv1 prize total^1等奖特制奖品总数不能大于1等奖可用奖品
//    MSG_500325,//500325^All Prize count can't greater than PrizeNum^所有奖项和不能大于总奖项
//    MSG_500408,//500408^lv1 prize loginName does not exist: {0}^1等奖中奖会员不存在 {0}
//    MSG_500411,//500411^lv2 prize loginName does not exist: {0}^2等奖中奖会员不存在 {0}
//    MSG_500410,//500410^it must be the same quantity between the lv2 prize and the people who win the lv2 prize^2等奖特制奖中奖会员数必须等于2等奖特制奖品数
//    MSG_540009,//540010^updated by cannot be null!^更新不能為空！
//    MSG_540010,//540010^topic id cannot be empty!^主題ID不能為空！
//    MSG_540001,//540001^StartTime  not correct format,must be yyyy-MM-dd hh24:mi:ss^开始时间格式不正确,格式必须为 yyyy-MM-dd hh24:mi:ss
//    MSG_540005,//540005^start time must not be empty^开始时间不能为空
//    MSG_540002,//540002^EndTime not correct format,must be yyyy-MM-dd hh24:mi:ss^结束时间 格式不正确,格式必须为 yyyy-MM-dd hh24:mi:ss
//    MSG_540004,//540004^end time must not be empty^结束时间必须不能为空
//    MSG_540007,//540007^customer level and deposit level cannot be null at the same time^同时，客户的水平和存款水平不能为空
//    MSG_540003,//540003^topic name must not be empty^主题名称不能为空
//    MSG_502021,//502021^GuessType  not right,must be 5,6,7,8,11,12  one of!^竞猜类型 不正确的值,值只能为 5,6,7,8,11,12 其中之一
//    MSG_500407,//500407^it must be the same quantity between the lv1 prize and the people who win the lv1 prize^1等奖特制奖中奖会员数必须等于1等奖特制奖品数
//    MSG_500414,//500414^lv3 prize loginName does not exist:  {0}^3等奖中奖会员不存在  {0}
//    MSG_500416,//500416^it must be the same quantity between the lv4 prize and the people who win the lv4 prize^4等奖特制奖中奖会员数必须等于4等奖特制奖品数
//    MSG_500415,//500415^Lv4 Special prize is not greater than the Lv4 prize total^4等奖特制奖品总数不能大于4等奖可用奖品
//    MSG_500506,//500506^customers must is real account^会员必须是真钱会员
//    MSG_531004,//532004^option id must not be empty^選項ID不能為空
//    MSG_531001,//532001^updated by must not be empty^更新不能為空
//    MSG_531002,//532002^option name must not be empty^选项名称不能为空
//    MSG_531003,//532003^topic id name must not be empty^主题ID名称不能为空
//    MSG_511002,//511002^option name must not be empty^选项名称不能为空
//    MSG_511003,//511003^topic id name must not be empty^主题ID名称不能为空
//    MSG_802001,//802001^vo should not be empty!^vo不能为空!
//    MSG_802002,//802002^LoginName should not be empty!^登录名不能为空!
//    MSG_802003,//802003^IP should not be empty!^IP登录名不能为空!
//    MSG_801004,//801004^tokenCode should not be empty!^Token 不能为空!
//    MSG_801005,//801005^siteId Must in 1-99!^子网站代号必须在1-99！
//    MSG_500412,//500412^Lv3 Special prize is not greater than the Lv3 prize total^3等奖特制奖品总数不能大于3等奖可用奖品
//    MSG_500413,//500413^it must be the same quantity between the lv3 prize and the people who win the lv3 prize^3等奖特制奖中奖会员数必须等于3等奖特制奖品数
//    MSG_206605,//206605^phone is empty or error^电话为空或格式错误
//    MSG_206607,//206607^Number is not verified^电话号码未绑定或未关联任何游戏账户不能快速登录！
//    MSG_304935,//304934^The REQUEST_ID not exist!^洗码提案REQUEST_ID不能为空！
//    MSG_304918,//304919^The request exist!^洗码提案已经存在！
//    MSG_348004,//348004^The Transfer mode must is OUT!^原单据转类型必须为OUT！
//    MSG_348005,//348005^Remarks must not be empty^备注 不能为空!
//    MSG_348006,//348006^The Transfer recodes flag must is 2！^转账单的状态必须为2！
//    MSG_348007,//348007^The flag must is -1 or 8！^状态必须为-1或者 8！
//    MSG_348008,//348008^LastUpdatedBy  must not be empty^最后更新人不能为空！
//    MSG_304311,//304311^ApproveBy must user^审批人类型只能是后台管理员用户
//    MSG_304312,//304312^Remarks length must for 1 to 300^备注 长度必须在 1 和 300 之间
//    MSG_310416,//310416^customers flag must be enabled^会员状态必须为启用
//    MSG_310415,//310415^customers must is real account^会员必须是真钱会员
//    MSG_346000,//346000^Transfer mode must OUT or IN!^转帐类型必须为OUT或IN！
//    MSG_201418,//201418^Point^积分不能为非数字
//    MSG_201419,//201419^Point^积分不能为0
//    MSG_201420,//201420^Type^类型不能为非数字
//    MSG_304502,//304502^The bill NO. has been applied！^对方的单号已经存在转账申请中！
//    MSG_510003,//510003^topic name must not be empty^主题名称不能为空
//    MSG_510006,//510006^created by must not be empty^创建人不能为空
//    MSG_510007,//510004^end time must not be empty^结束时间必须不能为空
//    MSG_510008,//510005^start time must not be empty^开始时间不能为空
//    MSG_202118,//202118^Customer does not exist^用户不存在
//    MSG_206608,//206608^The game account has been disabled, please contact customer service^游戏帐户已被禁用，请联系客服
//    MSG_300832,//300832^Update Email tokenCode error: {0}^Update Email tokenCode error:{0}
//    MSG_347007,//347007^The Transfer Flag is correct!^转账确认状态不正确！
//    MSG_347008,//347008^The Transfer mode is correct!^原单据转类型不为IN和OUT！
//    MSG_347005,//347005^The Transfer status cannot confirm!^此转账记录状态无法确认！
//    MSG_347004,//347004^flag should not be empty!^状态不能为空!
//    MSG_347009,//347009^remarks should not be empty!^备注不能为空!
//    MSG_347010,//347010^auditBy should not be empty!^审核人不能为空!
//    MSG_300503,//300503^customers must is real account^会员必须是真钱会员
//    MSG_300502,//300502^LoginName not exist!^登录名不存在
//    MSG_310607,//310607^customers flag must be enabled^会员状态必须为启用
//    MSG_310608,//310608^Try account can't the operator^试玩会员不能进行该操作
//    MSG_310609,//310609^WSCustomers's have unfinished Modify Idcard Request!^会员有未处理的修改证件号提案!
//    MSG_304367,//304367^Withdrwal in the audit,customer can not be canceled!^取款在审核中，顾客不能取消
//    MSG_304324,//304324^Customer cancel can only be himself!^会员取消必须为本人!
//    MSG_304357,//304357^customers flag must be enabled^会员状态必须为启用
//    MSG_304417,//304417^if Newflag is 2,Oldflag must for 0^新状态为2时，旧状态必须为0
//    MSG_304409,//304409^if Newflag is 2,Oldflag must for 1^新状态为2时，旧状态必须为1
//    MSG_304415,//304415^transfer out amount is not greater than customer's game credit!^转账金额不能超过会员本地游戏额度!
//    MSG_206613,//206613^Confirm number is not config^比特币确认次数未配置
//    MSG_206614,//206614^Confirm number is error^比特币确认次数不满足
//    MSG_801006,//801006^TokenType should not be empty!^Token类型不能为空!
//    MSG_206101,//206101^AgentName^合作伙伴已禁用
//    MSG_319906,//319906^customers flag must be enabled^会员状态必须为启用
//    MSG_319907,//319907^Try account can't the operator^试玩会员不能进行该操作
//    MSG_319909,//319909^{0}  have pending Modify Contract Request!^{0}会员有未处理的佣金方案修改提案!
//    MSG_319910,//319909^There is a pending Modify Contract Request!^有未处理的预设佣金方案修改提案!
//    MSG_500417,//500417^lv4 prize loginName does not exist: {0}^4等奖中奖会员不存在 {0}
//    MSG_500418,//500418^Lv5 Special prize is not greater than the Lv5 prize total^5等奖特制奖品总数不能大于5等奖可用奖品
//    MSG_500419,//500419^it must be the same quantity between the lv5 prize and the people who win the lv5 prize^5等奖特制奖中奖会员数必须等于5等奖特制奖品数
//    MSG_500420,//500420^lv5 prize loginName does not exist: {0}^5等奖中奖会员不存在 {0}
//    MSG_500421,//500421^Lv6 Special prize is not greater than the Lv6 prize total^6等奖特制奖品总数不能大于6等奖可用奖品
//    MSG_500423,//500423^lv6 prize loginName does not exist: {0}^6等奖中奖会员不存在 {0}
//    MSG_500424,//500424^Lv7 Special prize is not greater than the Lv7 prize total^7等奖特制奖品总数不能大于7等奖可用奖品
//    MSG_500425,//500425^it must be the same quantity between the lv7 prize and the people who win the lv7 prize^7等奖特制奖中奖会员数必须等于7等奖特制奖品数
//    MSG_500426,//500426^lv7 prize loginName does not exist:{0}^7等奖中奖会员不存在{0}
//    MSG_500427,//500427^Lv8 Special prize is not greater than the Lv8 prize total^8等奖特制奖品总数不能大于8等奖可用奖品
//    MSG_500428,//500428^it must be the same quantity between the lv8 prize and the people who win the lv8 prize^8等奖特制奖中奖会员数必须等于8等奖特制奖品数
//    MSG_500429,//500429^lv8 prize loginName does not exist: {0}^8等奖中奖会员不存在 {0}
//    MSG_500430,//500430^Lv9 Special prize is not greater than the Lv9 prize total^9等奖特制奖品总数不能大于9等奖可用奖品
//    MSG_500431,//500431^it must be the same quantity between the lv9 prize and the people who win the lv9 prize^9等奖特制奖中奖会员数必须等于9等奖特制奖品数
//    MSG_500432,//500432^lv9 prize loginName does not exist: {0}^9等奖中奖会员不存在 {0}
//    MSG_500433,//500433^Lv10 Special prize is not greater than the Lv10 prize total^10等奖特制奖品总数不能大于10等奖可用奖品
//    MSG_500434,//500434^it must be the same quantity between the lv10 prize and the people who win the lv10 prize^10等奖特制奖中奖会员数必须等于10等奖特制奖品数
//    MSG_500435,//500435^lv10 prize loginName does not exist: {0}^10等奖中奖会员不存在{0}
//    MSG_500436,//500436^Lv11 Special prize is not greater than the Lv11 prize total^11等奖特制奖品总数不能大于11等奖可用奖品
//    MSG_500437,//500437^it must be the same quantity between the lv11 prize and the people who win the lv11 prize^11等奖特制奖中奖会员数必须等于11等奖特制奖品数
//    MSG_500438,//500438^lv11 prize loginName does not exist: {0}^11等奖中奖会员不存在 {0}
//    MSG_500439,//500439^Lv12 Special prize is not greater than the Lv12 prize total^12等奖特制奖品总数不能大于12等奖可用奖品
//    MSG_500440,//500440^it must be the same quantity between the lv12 prize and the people who win the lv12 prize^12等奖特制奖中奖会员数必须等于12等奖特制奖品数
//    MSG_500441,//500441^lv12 prize loginName does not exist: {0}^12等奖中奖会员不存在 {0}
//    MSG_500442,//500442^Lv13 Special prize is not greater than the Lv13 prize total^13等奖特制奖品总数不能大于13等奖可用奖品
//    MSG_500443,//500443^it must be the same quantity between the lv13 prize and the people who win the lv13 prize^13等奖特制奖中奖会员数必须等于13等奖特制奖品数
//    MSG_500444,//500444^lv13 prize loginName does not exist: {0}^13等奖中奖会员不存在 {0}
//    MSG_500445,//500445^Lv14 Special prize is not greater than the Lv14 prize total^14等奖特制奖品总数不能大于14等奖可用奖品
//    MSG_500446,//500446^it must be the same quantity between the lv14 prize and the people who win the lv14 prize^14等奖特制奖中奖会员数必须等于14等奖特制奖品数
//    MSG_500447,//500447^lv14 prize loginName does not exist: {0}^14等奖中奖会员不存在 {0}
//    MSG_500448,//500448^Lv15 Special prize is not greater than the Lv15 prize total^15等奖特制奖品总数不能大于15等奖可用奖品
//    MSG_500449,//500449^it must be the same quantity between the lv15 prize and the people who win the lv15 prize^15等奖特制奖中奖会员数必须等于15等奖特制奖品数
//    MSG_500450,//500450^lv15 prize loginName does not exist: {0}^15等奖中奖会员不存在 {0}
//    MSG_201726,//201726^generate the verification code failed!^生成验证码失败！
//    MSG_362002,//362002^The modifyType must in i,2!^修改类型必须在1,2之中！
//    MSG_334001,//334001^the transfer type must IN or OUT^转账类型只能是IN或OUT
//    MSG_304613,//304613^transfer out amount is not greater than customer's credit!^转账金额不能超过会员本地额度!
//    MSG_304514,//304514^Cust Type^试玩不能转德州
//    MSG_304506,//304506^transfer out amount is not greater than customer's game credit!^转账金额不能超过会员本地游戏额度!
//    MSG_517005,//517005^count operation invalid!..^計數操作無效！
//    MSG_346111,//346111^transfer amount must less than the balance!^转账金额必须小于账户游戏余额！
//    MSG_211007,//211007^the relation between phone number and loginName is existing already^手机号登录己经设置过
//    MSG_211010,//211007^UnAssign default account and phone number^未关联手机号对应的默认帐号
//    MSG_211006,//211006^Bond Phone number is incorrect^用户未绑定有效的手机号码
//    MSG_211009,//211009^VerifyCode is incorrect^验证码错误
//    MSG_211001,//211001^ModifyPhoneLoginFlagRequest is empty^设置手机绑定登录标识参数为空
//    MSG_211004,//211004^LoginName not exist!^登录名不存在
//    MSG_211005,//211005^loginEnable  should not be empty!^是否关联手机号对应的默认帐号标识不能为空!
//    MSG_211008,//211008^code should not be empty!^验证码不能为空!
//    MSG_304955,//304955^batch commits XM,data is lost from the data center!^批量提交洗码时，从数据中心获取数据失败！
//    MSG_346106,//346106^openId not matched!^openId不匹配！
//    MSG_346011,//346011^third loginName not matched!^对方站登录名不匹配！
//    MSG_346112,//346112^amount should not be empty!^转出金额不能为空!
//    MSG_346006,//346006^openId not matched!^openId不匹配！
//    MSG_300603,//300603^customers must is real account^会员必须是真钱会员
//    MSG_362005,//362005^openId and loginName not matched!^openId和登录名不匹配！
//    MSG_361006,//361006^openId not matched!^openId不匹配！
//    MSG_201723,//201723^IP time error^该ip发送次数过于频繁，稍后再试。
//    MSG_201722,//201722^IP MAX^已超过24小时内最高发送次数
//    MSG_304952,//304952^When batch commits,the rebate time interval must be consistent!^批量提交洗码时，洗码的时间区间必须一致！
//    MSG_304323,//304323^Customer^会员不存在或已禁用
//    MSG_304917,//304919^The customer is exist!^会员不存在！
//    MSG_301773,//301773^The device has been consumer the promotion!^该设备已经领取过此类优惠！
//    MSG_509328,//509328^WSCustomers's have unfinished same type promotion!^会员有未完成的同类型优惠提案!
//    MSG_302951,//302951^WSQueryXMRequests have mast be not null!^洗码查询对象不能为空！
//    MSG_302952,//302951^GetXmTypeRequest have mast be not null!^洗码配置查询对象不能为空！
//    MSG_300704,//300704^The customer bank information is incomplete!^会员没有银行信息!
//    MSG_300703,//300703^Try account can't the operator^试玩会员不能进行该操作
//    MSG_506206,//506206^SendPhone^手机号格式错误
//    MSG_506205,//506205^Send times is not greater than 5^每天发送次数不能大于5。
//    MSG_570110,//570110^parent id must not be empty^父ID不能為空
//    MSG_560002,//560002^Topic id must not be empty!^主题ID不能为空!
//    MSG_560001,//560001^Customer id must not be empty!^客户ID不能为空!
//    MSG_504002,//504003^ParentId and CustomerId can not at the same time is empty^ParentId和CustomerId不能同时为空
//    MSG_511005,//511005^type must be 1 or -1^类型必须是1或-1
//    MSG_512002,//512002^options must not be empty^选项​​不能为空
//    MSG_503203,//503203^Try account can't the operator^试玩会员不能进行该操作
//    MSG_201712,//201712^Login name must to lowercase letters and Numbers composition^登录名必须为小写字母和数字组成
//    MSG_201713,//201713^LoginName can't repeat!^会员登录名不能重复!
//    MSG_206703,//206702^loginName number error^登录名个数不能大于30
//    MSG_202211,//202211^CUSTOMER_ID,GAME_ID,GAME_KIND Add data may not be repeated^根据CUSTOMER_ID,GAME_ID,GAME_KIND 添加的数据不能重复
//    MSG_203022,//203022^Invalid Flag status^Flag 标示为空
//    MSG_201903,//201903^customers must is real account^会员必须是真钱会员
//    MSG_500409,//500409^Lv2 Special prize is not greater than the Lv2 prize total^2等奖特制奖品总数不能大于2等奖可用奖品
//    MSG_500505,//500505^prizeNum between 0 to 300^抽奖次数必须大于0小于300
//    MSG_502822,//502822^PeriodNum is exist^期数已存在
//    MSG_501507,//501507^maxTimes,beginTimes,endTimes Cannot at the same time is empty^maxTimes,beginTimes,endTimes 不能同时为空
//    MSG_206604,//206605^Transfer account inconformity^绑定账号不一致
//    MSG_206612,//206612^Transfer amount greater than the limit^转账金额大于每天限额
//    MSG_206603,//206606^Transfer amount not less than zero^转账金额不能小于0
//    MSG_206609,//206607^Transfer amount not enough^转账金额大于当前额度
//    MSG_201404,//201404^Try account can't the operator^试玩会员不能进行该操作
//    MSG_804012,//804012^tokenCode must generate by login!^TokenCode必须通过登录生成！
//    MSG_502027,//502027^flag not right,must be 9 one of!^flag 不正确的值,值只能为 9 其中之一
//    MSG_502121,//502121^GuessType not right,must be 1,2,3,4,5,6,7,8,9,10,11,12 one of!^竞猜类型 不正确的值,值只能为 1,2,3,4,5,6,7,8,9,10,11,12 其中之一
//    MSG_502130,//502130^flag not right,must be 0,9 one of!^flag 不正确的值,值只能为 0,9 其中之一
//    MSG_502133,//502133^GuessType not right,must be 1,2,3,4,9,10 one of!^竞猜类型 不正确的值,值只能为 1,2,3,4,9,10 其中之一
//    MSG_502136,//502136^GuessType not right,must be 5,6,7,8,11,12 one of!^竞猜类型 不正确的值,值只能为 5,6,7,8,11,12 其中之一
//    MSG_502206,//502206^Flag not right,must be 2,-3 one of!^竞猜结果标识 不正确的值,值只能为 2,-3 其中之一
//    MSG_500904,//500904^PromotionFlag not right,must be 1,2,-2,-1 one of!^优惠标识 不正确的值,值只能为 1,2,-2,-1 其中之一
//    MSG_506202,//506202^SendType not right,must be 60018 one of!^短信类型 不正确的值,值只能为 60018 其中之一
//    MSG_310603,//310603^UserType not right,must be U one of!^用户类型 不正确的值,值只能为 U 其中之一
//    MSG_304602,//304602^Action not right,must be IN one of!^操作类型 不正确的值,值只能为 IN 其中之一
//    MSG_304603,//304603^Oldflag not right,must be 0 one of!^旧转账状态 不正确的值,值只能为 0 其中之一
//    MSG_304604,//304604^Newflag not right,must be 1 one of!^新转账状态 不正确的值,值只能为 1 其中之一
//    MSG_304609,//304609^UserType not right,must be U,C one of!^用户类型 不正确的值,值只能为 U,C 其中之一
//    MSG_304406,//304406^Newflag not right,must be 2,-2 one of!^新转账状态 不正确的值,值只能为 2,-2 其中之一
//    MSG_304408,//304408^Oldflag not right,must be 0,1 one of!^旧转账状态 不正确的值,值只能为 0,1 其中之一
//    MSG_310404,//310404^LotteryType not right,must be 0,1,2 one of!^奖卷类型 不正确的值,值只能为 0,1,2 其中之一
//    MSG_310417,//310417^CreditType not right,must be 0,1 one of!^货币类型 不正确的值,值只能为 0,1 其中之一
//    MSG_501611,//501611^Special not right,must be 1,2 one of!^是否特制奖品 不正确的值,值只能为 1,2 其中之一
//    MSG_501612,//501612^PrizeType not right,must be 0,1 one of!^奖品类型 不正确的值,值只能为 0,1 其中之一
//    MSG_501208,//501208^PrizeType not right,must be 0,1 one of!^奖品类型 不正确的值,值只能为 0,1 其中之一
//    MSG_502903,//502903^Flag not right,must be 0,1 one of!^状态 不正确的值,值只能为 0,1 其中之一
//    MSG_304507,//304507^UserType not right,must be U,C one of!^用户类型 不正确的值,值只能为 U,C 其中之一
//    MSG_304513,//304513^ApTransferType not right,must be A,B one of!^AP操作类型 不正确的值,值只能为 A,B 其中之一
//    MSG_301003,//301003^RequestType not right,must be 01,02 one of!^提案类型 不正确的值,值只能为 01,02 其中之一
//    MSG_301004,//301004^StatusType not right,must be 1,2,3 one of!^短信邮件状态类型 不正确的值,值只能为 1,2,3 其中之一
//    MSG_301005,//301005^SmsStatus not right,must be 1111,2000,5000 one of!^短信发送状态 不正确的值,值只能为 1111,2000,5000 其中之一
//    MSG_301006,//301006^EmailStatus not right,must be 1111,2000,5000 one of!^邮件发送状态 不正确的值,值只能为 1111,2000,5000 其中之一
//    MSG_301007,//301007^SmsStatus not right,must be 1111,2000,5000 one of!^短信发送状态 不正确的值,值只能为 1111,2000,5000 其中之一
//    MSG_301008,//301008^EmailStatus not right,must be 1111,2000,5000 one of!^邮件发送状态 不正确的值,值只能为 1111,2000,5000 其中之一
//    MSG_501810,//501810^promotion_flag not right,must be 0,1,2,-3,-1 one of!^状态 不正确的值,值只能为 0,1,2,-3,-1 其中之一
//    MSG_307302,//307302^Action not right,must be IN,OUT one of!^操作类型 不正确的值,值只能为 IN,OUT 其中之一
//    MSG_307303,//307303^Oldflag not right,must be 2 one of!^旧转账状态 不正确的值,值只能为 2 其中之一
//    MSG_307304,//307304^Newflag not right,must be 9,-9 one of!^新转账状态 不正确的值,值只能为 9,-9 其中之一
//    MSG_501911,//501911^Special not right,must be 1,2 one of!^是否特制奖品 不正确的值,值只能为 1,2 其中之一
//    MSG_501912,//501912^PrizeType not right,must be 0,1 one of!^奖品类型 不正确的值,值只能为 0,1 其中之一
//    MSG_501510,//501510^promotion_flag not right,must be 0,1,2,-3,-1 one of!^状态 不正确的值,值只能为 0,1,2,-3,-1 其中之一
//    MSG_501008,//501008^PrizeType not right,must be 0,1 one of!^奖品类型 不正确的值,值只能为 0,1 其中之一
//    MSG_201800,//201800^type not right,must be 0,1 one of!^类别 不正确的值,值只能为 0,1 其中之一
//    MSG_201101,//201101^flag not right,must be 2,-2 one of!^flag 不正确的值,值只能为 2,-2 其中之一
//    MSG_202016,//202016^CustomerType not right,must be 1,2,3 one of!^会员类型 不正确的值,值只能为 1,2,3 其中之一
//    MSG_319903,//319903^UserType not right,must be U one of!^用户类型 不正确的值,值只能为 U 其中之一
//    MSG_804008,//804008^TokenType not right,must be 0,1,2,3,4,5,6,7,99 one of!^Token类型 不正确的值,值只能为 0,1,2,3,4,5,6,7,99 其中之一
//    MSG_202203,//202203^RATE must be greater than zero,format must be **.00^洗码比必须大于0,格式必须为**.00
//    MSG_313007,//313007^Amount must be greater than zero,format must be **.00^金额必须大于0,格式必须为**.00
//    MSG_804007,//804007^tokenCode error: {0}^TokenCode错误: {0}
//    MSG_402801,//402801^CreatedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss !^创建时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402802,//402802^CreatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss !^创建时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402803,//402803^EffectivityDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss !^有效时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402804,//402804^EffectivityDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss !^有效时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402805,//402805^ExpiryDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss !^逾期时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402806,//402806^ExpiryDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss !^逾期时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402807,//402807^LastUpdateDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss !^最后修改时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_402808,//402808^LastUpdateDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss !^最后修改时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_502003,//502003^ValidAccount not correct format,must be **.00^优惠金额起格式不正确,格式必须为**.00
//    MSG_500101,//500101^calculateDate not correct format,must be yyyy-MM-dd^需要计算连赢的日期起格式不正确,格式必须为yyyy-MM-dd
//    MSG_502708,//502708^MatchEndDate not correct format,must be yyyy-MM-dd^比赛结束时间起格式不正确,格式必须为yyyy-MM-dd
//    MSG_502709,//502709^ResultCalculateDate not correct format,must be yyyy-MM-dd^比赛结果统计时间起格式不正确,格式必须为yyyy-MM-dd
//    MSG_502710,//502710^ResultAnnounceDate not correct format,must be yyyy-MM-dd^比赛结果公布时间起格式不正确,格式必须为yyyy-MM-dd
//    MSG_502711,//502711^Account not correct format,must be **.00^初始本金起格式不正确,格式必须为**.00
//    MSG_502712,//502712^GameNum must be a positive integer, the length is more than 18!^比赛局数必须是一个正整数,长度不超过18
//    MSG_502713,//502713^LimitAccount not correct format,must be **.00^限红起格式不正确,格式必须为**.00
//    MSG_502715,//502715^Flag must be a positive integer, the length is more than 1!^活动状态必须是一个正整数,长度不超过1
//    MSG_502716,//502716^MaxSignNum must be a positive integer, the length is more than 18!^最大报名人数必须是一个正整数,长度不超过18
//    MSG_502703,//502703^StartDate not correct format,must be yyyy-MM-dd^活动开始时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_502704,//502704^EndDate not correct format,must be yyyy-MM-dd^活动结束时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_502705,//502705^SignStartDate not correct format,must be yyyy-MM-dd^报名开始时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_502706,//502706^SignEndDate not correct format,must be yyyy-MM-dd^报名结束时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_502707,//502707^MatchStartDate not correct format,must be yyyy-MM-dd^比赛开始时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_502717,//502711^BetAmount not correct format,must be **.00^有效投资额格式不正确,格式必须为**.00
//    MSG_502718,//502712^Rank must be a positive integer,the length is more than 18!^名次必须是一个正整数,长度不超过18
//    MSG_502719,//502712^BetTimes must be a positive integer, the length is more than 18!^投注次数必须是一个正整数,长度不超过18
//    MSG_502720,//502713^WinAmount not correct format,must be **.00^输赢值格式不正确,格式必须为**.00
//    MSG_502103,//502103^PromotionAmount not correct format,must be **.00^优惠金额格式不正确,格式必须为**.00
//    MSG_500403,//500403^validDate not correct format,must be yyyy-MM-dd^有效时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_500206,//500206^Account not correct format,must be **.0000^投注额格式不正确,格式必须为**.0000
//    MSG_500207,//500207^ValidAccount not correct format,must be **.0000^有效投注额格式不正确,格式必须为**.0000
//    MSG_500208,//500208^CusAccount not correct format,must be **.0000^客户输赢度格式不正确,格式必须为**.0000^**.0000
//    MSG_500210,//500210^WinSequence not correct format,must be number^连赢序号格式不正确,格式必须为number
//    MSG_500214,//500214^Billtime not correct format,must be yyyy-MM-dd HH:mm:ss^下注时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_500215,//500215^Reckontime not correct format,must be yyyy-MM-dd HH:mm:ss^注单更新时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_500220,//500220^CreationTime not correct format,must be yyyy-MM-dd HH:mm:ss^数据在数据中心创建时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_500222,//500222^WinFlag not correct format,must be number^记录是否为同一次连赢(连赢次数)格式不正确,格式必须为number
//    MSG_500213,//500213^Gametype not correct format,must be number^游戏玩法格式不正确,格式必须为number
//    MSG_310402,//310402^Prizeid not correct format,must be number^奖品ID格式不正确,格式必须为number
//    MSG_310405,//310405^LotteryDepositAmount not correct format,must be number^奖卷使用存款金额格式不正确,格式必须为number
//    MSG_310406,//310406^LotteryBetAmount not correct format,must be number^奖卷使用有效投注额格式不正确,格式必须为number
//    MSG_310407,//310407^Amount not correct format,must be number^奖卷金额格式不正确,格式必须为number
//    MSG_310408,//310408^BetAmountMultiplier not correct format,must be number^奖金取款所需有效投注额倍数格式不正确,格式必须为number
//    MSG_310409,//310409^Validdate not correct format,must be yyyy-MM-dd 00:00:00^有效时间格式不正确,格式必须为yyyy-MM-dd 00:00:00
//    MSG_500303,//500303^validDate not correct format,must be yyyy-MM-dd^有效日期格式不正确,格式必须为yyyy-MM-dd
//    MSG_500306,//500306^Amount1 must be a positive integer, the length is more than 5!^第1奖项金额必须是一个正整数,长度不超过5
//    MSG_500307,//500307^Num1 must be a positive integer, the length is more than 3!^第1奖项数量必须是一个正整数,长度不超过3
//    MSG_500308,//500308^Multiplier1 must be a positive integer, the length is more than 9!^第1奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500309,//500309^Amount2 must be a positive integer, the length is more than 5!^第2奖项金额必须是一个正整数,长度不超过5
//    MSG_500310,//500310^Num2 must be a positive integer, the length is more than 3!^第2奖项数量必须是一个正整数,长度不超过3
//    MSG_500311,//500311^Multiplier2 must be a positive integer, the length is more than 9!^第2奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500312,//500312^Amount1 must be a positive integer, the length is more than 5!^第3奖项金额必须是一个正整数,长度不超过5
//    MSG_500313,//500313^Num3 must be a positive integer, the length is more than 3!^第3奖项数量必须是一个正整数,长度不超过3
//    MSG_500314,//500314^Multiplier3 must be a positive integer, the length is more than 9!^第3奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500315,//500315^Amount1 must be a positive integer, the length is more than 5!^第4奖项金额必须是一个正整数,长度不超过5
//    MSG_500316,//500316^Num4 must be a positive integer, the length is more than 3!^第4奖项数量必须是一个正整数,长度不超过3
//    MSG_500317,//500317^Multiplier4 must be a positive integer, the length is more than 9!^第4奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500318,//500318^Amount1 must be a positive integer, the length is more than 5!^第5奖项金额必须是一个正整数,长度不超过5
//    MSG_500319,//500319^Num5 must be a positive integer, the length is more than 3!^第5奖项数量必须是一个正整数,长度不超过3
//    MSG_500320,//500320^Multiplier5 must be a positive integer, the length is more than 9!^第5奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500321,//500321^Amount1 must be a positive integer, the length is more than 5!^第6奖项金额必须是一个正整数,长度不超过5
//    MSG_500322,//500322^Num6 must be a positive integer, the length is more than 3!^第6奖项数量必须是一个正整数,长度不超过3
//    MSG_500323,//500323^Multiplier6 must be a positive integer, the length is more than 9!^第6奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500009,//500325^Amount1 must be a positive integer, the length is more than 5!^第7奖项金额必须是一个正整数,长度不超过5
//    MSG_500326,//500326^Num7 must be a positive integer, the length is more than 3!^第7奖项数量必须是一个正整数,长度不超过3
//    MSG_500327,//500327^Multiplier7 must be a positive integer, the length is more than 9!^第7奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500328,//500328^Amount1 must be a positive integer, the length is more than 5!^第8奖项金额必须是一个正整数,长度不超过5
//    MSG_500329,//500329^Num8 must be a positive integer, the length is more than 3!^第8奖项数量必须是一个正整数,长度不超过3
//    MSG_500330,//500330^Multiplier8 must be a positive integer, the length is more than 9!^第8奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500331,//500331^Amount1 must be a positive integer, the length is more than 5!^第9奖项金额必须是一个正整数,长度不超过5
//    MSG_500332,//500332^Num9 must be a positive integer, the length is more than 3!^第9奖项数量必须是一个正整数,长度不超过3
//    MSG_500333,//500333^Multiplier9 must be a positive integer, the length is more than 9!^第9奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_304305,//304305^UserType not right,must be U,C,Z,S one of!^用户类型 不正确的值,值只能为 U,C,Z,S 其中之一
//    MSG_304313,//304313^RequestType not right,must be 01,02,03,04,05,06,07,08,09,11,12,13,14,15,16,17,20,21,23,24,26,27,28,29,30,31,32 one of!^提案类型 不正确的值,值只能为 01,02,03,04,05,06,07,08,09,11,12,13,14,15,16,17,20,21,23,24,26,27,28,29,30,31,32 其中之一
//    MSG_304314,//304314^Oldflag not right,must be 0 one of!^提案旧状态 不正确的值,值只能为 0 其中之一
//    MSG_304315,//304315^Newflag not right,must be 2,-3 one of!^提案新状态 不正确的值,值只能为 2,-3 其中之一
//    MSG_304318,//304318^Oldflag not right,must be 0,1,9,8 one of!^提案旧状态 不正确的值,值只能为 0,1,9,8 其中之一
//    MSG_304319,//304319^Newflag not right,must be -2,-1,1,9 one of!^提案新状态 不正确的值,值只能为 -2,-1,1,9 其中之一
//    MSG_305323,//305323^Newflag not right,must be 1,-2,-1 one of!^提案新状态 不正确的值,值只能为 1,-2,-1 其中之一
//    MSG_305324,//305324^Newflag not right,must be 0,-2,-1 one of!^提案新状态 不正确的值,值只能为 0,-2,-1 其中之一
//    MSG_305325,//305325^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_304325,//304325^Oldflag not right,must be 0,9 one of!^提案旧状态 不正确的值,值只能为 0,9 其中之一
//    MSG_304326,//304326^Newflag not right,must be 9,-1,-2,2,-3 one of!^提案新状态 不正确的值,值只能为 9,-1,-2,2,-3 其中之一
//    MSG_305326,//305326^Newflag not right,must be -3,2,-1,-2 one of!^提案新状态 不正确的值,值只能为 -3,2,-1,-2 其中之一
//    MSG_304329,//304329^Oldflag not right,must be 0 one of!^提案旧状态 不正确的值,值只能为 0 其中之一
//    MSG_304330,//304330^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_304341,//304341^Oldflag not right,must be 0,9 one of!^提案旧状态 不正确的值,值只能为 0,9 其中之一
//    MSG_304342,//304342^Newflag not right,must be -3,2,9 one of!^提案新状态 不正确的值,值只能为 -3,2,9 其中之一
//    MSG_305327,//305327^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_305328,//305328^Oldflag not right,must be 0 one of!^提案旧状态 不正确的值,值只能为 0 其中之一
//    MSG_305329,//305329^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_305330,//305330^Oldflag not right,must be 0 one of!^提案旧状态 不正确的值,值只能为 0 其中之一
//    MSG_305331,//305331^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_304353,//304353^Oldflag not right,must be 0,9 one of!^提案旧状态 不正确的值,值只能为 0,9 其中之一
//    MSG_304354,//304354^Newflag not right,must be -5,-3,2,0 one of!^提案新状态 不正确的值,值只能为 -5,-3,2,0 其中之一
//    MSG_304358,//304358^Oldflag not right,must be 0,1,9 one of!^提案旧状态 不正确的值,值只能为 0,1,9 其中之一
//    MSG_304359,//304359^Newflag not right,must be -3,2,1,9 one of!^提案新状态 不正确的值,值只能为 -3,2,1,9 其中之一
//    MSG_305332,//305332^Oldflag not right,must be 0 one of!^提案旧状态 不正确的值,值只能为 0 其中之一
//    MSG_305333,//305333^Newflag not right,must be -3,2 one of!^提案新状态 不正确的值,值只能为 -3,2 其中之一
//    MSG_305334,//305334^Oldflag not right,must be 0,2 one of!^预审表旧状态 不正确的值,值只能为 0,2 其中之一
//    MSG_305335,//305335^Newflag not right,must be -2,2,-3 one of!^预审表新状态 不正确的值,值只能为 -2,2,-3 其中之一
//    MSG_305336,//305336^Newflag not right,must be 3,-4 one of!^预审表新状态 不正确的值,值只能为 3,-4 其中之一
//    MSG_305337,//305337^Oldflag not right,must be 0,1,9 one of!^提案旧状态 不正确的值,值只能为 0,1,9 其中之一
//    MSG_305338,//305338^Newflag not right,must be 9,-3 one of!^提案新状态 不正确的值,值只能为 9,-3 其中之一
//    MSG_305339,//305339^Newflag not right,must be 2,-3 one of!^提案新状态 不正确的值,值只能为 2,-3 其中之一
//    MSG_305340,//305340^Newflag not right,must be -3,2,1 one of!^提案新状态 不正确的值,值只能为 -3,2,1 其中之一
//    MSG_304317,//304317^Oldflag and requestFlag Don't match!^提案旧状态和数据库状态不匹配
//    MSG_304361,//304361^Oldflag and requestFlag not equal Don't match!^提案旧状态和数据库状态不一致不匹配
//    MSG_801009,//801009^userName/password Don't match!^用户名/密码不匹配
//    MSG_811002,//811002^reference_id and product siteId Don't match!^reference_id 和 产品siteId不匹配
//    MSG_304365,//304365^customerId and loginName Don't match!^客户ID和登录名不匹配
//    MSG_304416,//304416^Oldflag and requestFlag Don't match!^转账旧状态和数据库状态不匹配
//    MSG_316001,//316001^Id not correct format,must be number^ID格式不正确,格式必须为number
//    MSG_316004,//316004^LastUpdateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^最后修改时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_316005,//316005^LastUpdateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^最后修改时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_502501,//502501^winDate not correct format,must be yyyy-MM-dd^连赢日期格式不正确,格式必须为yyyy-MM-dd
//    MSG_502305,//502305^Flag not correct format,must be number^标识格式不正确,格式必须为number
//    MSG_501003,//501003^TActivityPrizeId() not correct format,must be number^奖品ID格式不正确,格式必须为number
//    MSG_501004,//501004^Amount not correct format,must be number^中奖金额格式不正确,格式必须为number
//    MSG_502904,//502903^Credit not correct format,must be **.00^最终额度格式不正确,格式必须为**.00
//    MSG_502906,//502906^MinBetting not correct format,must be **.00^最小投资额度格式不正确,格式必须为**.00
//    MSG_310307,//310307^LotteryType not correct format,must be number^奖卷类型格式不正确,格式必须为number
//    MSG_310308,//310308^ValiddateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^有效时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_310309,//310309^ValiddateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^有效时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_310310,//310310^UseDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^使用时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_310311,//310311^UseDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^使用时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501107,//501107^Playtype not correct format,must be number^游戏玩法格式不正确,格式必须为number
//    MSG_501108,//501108^BilltimeBegin not correct format,must be yyyy-MM-dd HH:mm:ss^下注时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501109,//501109^BilltimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss^下注时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501110,//501110^ReckontimeBegin not correct format,must be yyyy-MM-dd HH:mm:ss^注单更新时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501111,//501111^CreationTimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss^注单更新时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501112,//501112^PreviosAmount not correct format,must be **.0000^下注前额度格式不正确,格式必须为**.0000
//    MSG_501115,//501115^CreationTimeBegin not correct format,must be yyyy-MM-dd HH:mm:ss^数据中心创建时间止起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501116,//501116^CreationTimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss^数据中心创建时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501118,//501118^AWLId not correct format,must be number^AWLId格式不正确,格式必须为number
//    MSG_502303,//502303^GuessType not correct format,must be number^竞猜类型格式不正确,格式必须为number
//    MSG_501702,//501702^WinTimes not correct format,must be number^连赢次数格式不正确,格式必须为number
//    MSG_518004,//518004^LastUpdatedDateStart not correct format,must be yyyy-MM-dd HH:mm:ss^最後更新日期開始格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_518003,//518003^LastUpdatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^最後更新日期結束格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_518005,//518005^EndTimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss^結束時間結束格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_518006,//518006^EndTimeStart not correct format,must be yyyy-MM-dd HH:mm:ss^結束時間開始格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_518007,//518007^StartTimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss^開始時間結束格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_518008,//518008^StartTimeStart not correct format,must be yyyy-MM-dd HH:mm:ss^開始時間開始格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_501604,//501604^Type not correct format,must be number^奖品等级格式不正确,格式必须为number
//    MSG_501605,//501605^Amount not correct format,must be number^中奖金额格式不正确,格式必须为number
//    MSG_501606,//501606^BetAmountMultiplier not correct format,must be number^奖品有效投注额倍数格式不正确,格式必须为number
//    MSG_307314,//307314^Oldflag and requestFlag Don't match!^转账旧状态和数据库状态不匹配
//    MSG_319904,//319904^CreatedBy not exist!^创建人不存在
//    MSG_300702,//300702^LoginName not exist!^登录名不存在
//    MSG_500903,//500903^WSWinConsecutiveLogs ID not exist!^连赢记录日志ID不存在
//    MSG_500906,//500906^Promotion RequestID not exist!^优惠提案ID不存在
//    MSG_200313,//200313^AgentID not exist!^代理登陆ID不存在
//    MSG_504003,//504003^LastUpdatedBy not exist!^最后修改人不存在
//    MSG_502203,//502203^WSActivityEurocupGuess ID not exist!^竞猜ID不存在
//    MSG_206102,//206101^AgentName not exist!^代理登陆名不存在
//    MSG_304610,//304610^SureBy not exist!^转账确认人不存在
//    MSG_304612,//304612^Transfer customer not exist!^转账会员不存在
//    MSG_201403,//201403^CustomerId not exist!^会员ID不存在
//    MSG_501504,//501504^maxTimes not correct format,must be number^maxTimes格式不正确,格式必须为number
//    MSG_501505,//501505^beginTimes not correct format,must be number^beginTimes格式不正确,格式必须为number
//    MSG_501506,//501506^endTimes not correct format,must be number^endTimes格式不正确,格式必须为number
//    MSG_501508,//501508^beginwinDate not correct format,must be yyyy-MM-dd^时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_501509,//501509^endwinDate not correct format,must be yyyy-MM-dd^时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_240113,//240113^Flag not correct format,must be number^会员状态格式不正确,格式必须为number
//    MSG_240114,//240114^CommissionType not correct format,must be number^佣金类型格式不正确,格式必须为number
//    MSG_240121,//240121^IsMarket not correct format,must be number^是否发展下线格式不正确,格式必须为number
//    MSG_240124,//240124^DownlineArbit not correct format,must be number^下线疑似套利格式不正确,格式必须为number
//    MSG_240115,//240115^AgentContractCode not correct format,must be number^方案Code格式不正确,格式必须为number
//    MSG_500910,//500910^PromotionAmount not correct format,must be **.0000^优惠额度格式不正确,格式必须为 **.0000
//    MSG_200202,//200202^CreatedDateBegin not correct format,must be yyyy-MM-dd^创建时间起格式不正确,格式必须为yyyy-MM-dd
//    MSG_200203,//200203^CreatedDateEnd not correct format,must be yyyy-MM-dd^创建时间止格式不正确,格式必须为yyyy-MM-dd
//    MSG_200206,//200206^AgentId not correct format,must be number^代理ID格式不正确,格式必须为number
//    MSG_206105,//206105^Contribution Month not correct format,must be yyyy-MM-dd^佣金月份格式不正确,格式必须为yyyy-MM-dd
//    MSG_206106,//206105^Start Date not correct format,must be yyyy-MM-dd^佣金开始时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_206107,//206105^End Date not correct format,must be yyyy-MM-dd^佣金结束时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_200302,//200302^Condition not correct format,must be JSONArray^佣金方案明细格式不正确,格式必须为JSONArray
//    MSG_200314,//200314^LastUpdate not correct format,must be yyyy-MM-dd HH:mm:ss^修改时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_200312,//200312^ContractId not correct format,must be number^佣金方案ID格式不正确,格式必须为number
//    MSG_206301,//206301^ContribStart not correct format,must be yyyy-MM-dd^佣金开始时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_206302,//206302^ContribEnd not correct format,must be ^佣金结束时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_303107,//303107^AuditDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^审核时间起格式不正确,格式必须为^yyyy-MM-dd HH:mm:ss
//    MSG_303108,//303108^AuditDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^审核时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_202905,//202905^PromotionConfigID not correct format,must be number^优惠配置ID格式不正确,格式必须为number
//    MSG_503204,//503204^BettingTimes not correct format,must be number^下注次数格式不正确,格式必须为number
//    MSG_503205,//503205^SubCustomerCount not correct format,must be number^下线用户数量格式不正确,格式必须为number
//    MSG_503206,//503206^BettingAmount not correct format,must be **.000000^下注金额格式不正确,格式必须为**.000000
//    MSG_503207,//503207^ValidAmount not correct format,must be **.000000^有效投注额格式不正确,格式必须为**.000000
//    MSG_503208,//503208^Income not correct format,must be **.000000^收入格式不正确,格式必须为**.000000
//    MSG_503209,//503209^BettingDate not correct format,must be yyyy-MM-dd^下注时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_503803,//503803^Parent ID not correct format,must be number^父ID格式不正确,格式必须为number
//    MSG_200310,//200310^NewCondition not correct format,must be json^方案明细格式不正确,格式必须为json
//    MSG_600202,//600202^DepositAmount not correct format,must be number^存款总额格式不正确,格式必须为number
//    MSG_600203,//600203^DepositCount not correct format,must be number^存款次数格式不正确,格式必须为number
//    MSG_600204,//600204^ElectronicAmount not correct format,must be ^电子游艺格式不正确,格式必须为number
//    MSG_600205,//600205^GamerAmount not correct format,must be number^真人娱乐格式不正确,格式必须为number
//    MSG_600206,//600206^LoginCount not correct format,must be number^登陆次数格式不正确,格式必须为number
//    MSG_600207,//600207^lotteryAmount not correct format,must be number^彩票格式不正确,格式必须为number
//    MSG_600209,//600209^SportAmount not correct format,must be number^体育格式不正确,格式必须为number
//    MSG_600210,//600210^TotalAmount not correct format,must be number^有效投注格式不正确,格式必须为number
//    MSG_600211,//600211^WithdrawalAmount not correct format,must be number^提款总额格式不正确,格式必须为number
//    MSG_600212,//600212^WithdrawalCount not correct format,must be number^提款次数格式不正确,格式必须为number
//    MSG_600213,//600213^XmAmount not correct format,must be number^洗码总额格式不正确,格式必须为number
//    MSG_600208,//600208^PromotionAmount not correct format,must be number^优惠总额格式不正确,格式必须为number
//    MSG_307201,//307201^CustomerType not correct format,must be number^会员类型格式不正确,格式必须为number
//    MSG_307202,//307202^CustomerLevel not correct format,must be number^会员级别格式不正确,格式必须为number
//    MSG_305901,//305901^BankId not correct format,must be number^存款银行ID格式不正确,格式必须为number
//    MSG_305902,//305902^LimitAmount not correct format,must be ^当天限额格式不正确,格式必须为number
//    MSG_201720,//201720^Phone not correct format,must have 11 digits^会员电话格式不正确,必须位数为11位
//    MSG_203503,//203503^CustomerBankId not correct format,must be number^会员银行ID格式不正确,格式必须为number
//    MSG_203505,//203505^PriorityOrder not correct format,must be number^银行优先标识格式不正确,格式必须为number
//    MSG_303005,//303005^PriorityType not correct format,must be number^优先类型格式不正确,格式必须为number
//    MSG_304103,//304103^ProcessedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^取款处理时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_304104,//304104^ProcessedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^取款处理时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_304109,//304109^SameFlag not correct format,must be number^优先类型格式不正确,格式必须为number
//    MSG_200309,//200309^CommissionType not exist!^新佣金类型不存在
//    MSG_200308,//200310^NewCondition not exist!^方案明细不存在
//    MSG_301009,//301009^Deposit RequestID not exist!^存款提案ID不存在
//    MSG_301010,//301010^Withdrawal RequestID not exist!^取款提案ID不存在
//    MSG_201417,//201417^CreatedBy not exist!^创建人不存在
//    MSG_304413,//304413^ReferenceId not exist!^转帐ID不存在
//    MSG_348003,//348003^requestId not exist!^转账单号不存在
//    MSG_304308,//304308^ApproveBy not exist!^审批人不存在
//    MSG_304362,//304362^SC Promotion no direct approval not exist!^首存优惠不能直接批准不存在
//    MSG_304331,//304331^XM RequestID not exist!^洗码提案ID不存在
//    MSG_304343,//304343^Modify Email RequestID not exist!^修改会员邮箱提案ID不存在
//    MSG_304344,//304344^Modify Idcard RequestID not exist!^修改会员证件号提案ID不存在
//    MSG_304347,//304347^Modify Real Name RequestID not exist!^修改会员真实姓名提案ID不存在
//    MSG_304355,//304355^Modify Parent RequestID not exist!^修改会员上线提案ID不存在
//    MSG_304356,//304356^Recommend RequestID not exist!^好友推荐提案ID不存在
//    MSG_304360,//304360^Commission Exchange RequestID not exist!^取款提案ID不存在
//    MSG_304363,//304357^AccountName not exist!^推荐人账号不存在
//    MSG_304364,//304358^CreditTransfer RequestID not exist!^额度互转提案ID不存在
//    MSG_304366,//304323^Customer ApproveBy not exist!^会员审批人不存在
//    MSG_304335,//304335^Request count length is not greater than 1000!^洗码提案数量长度不能大于1000！
//    MSG_201738,//201738^Id card number length should be 15 and 18.^身份证号码长度应该为15位或18位.
//    MSG_201739,//201739^Id card 15 org.codehaus.commons.compiler.util.Numbers should be Numbers; 18 number except the last one, it should be digital.^身份证15位号码都应为数字 ; 18位号码除最后一位外,都应为数字.
//    MSG_201740,//201740^Id card birthday is invalid.^身份证生日无效.
//    MSG_201741,//201741^Id card birthday is not in the valid range.^身份证生日不在有效范围.
//    MSG_201742,//201742^Id card Month is invalid.^身份证月份无效.
//    MSG_201743,//201743^Id card date is invalid.^身份证日期无效.
//    MSG_201744,//201744^Id card region coding errors.^身份证地区编码错误.
//    MSG_201745,//201745^Id card is invalid, not a legal id card number.^身份证无效,不是合法的身份证号码.
//    MSG_201416,//201416^CreatedBy should not be empty,and length must for 2 to 20 between!^创建人不能为空,长度必须在 2 和 20之间
//    MSG_502908,//502908^LastUpdatedBy should not be empty,and length must for 2 to 20 between!^修改人不能为空,长度必须在 2 和 20之间
//    MSG_506902,//506902^IssueNo should not be empty,and length must for 1 to 20 between!^活动期数不能为空,长度必须在 1 和 20之间
//    MSG_506903,//506903^PrizeNum should not be empty,and length must for 1 to 20 between!^抽奖号码不能为空,长度必须在 1 和 20之间
//    MSG_506906,//506906^ID should not be empty,and length must for 1 to 20 between!^ID不能为空,长度必须在 1 和 20之间
//    MSG_313002,//313002^ReferenceId should not be empty,and length must for 2 to 20 between!^关联单号不能为空,长度必须在 2 和 20之间
//    MSG_313008,//313008^Reserve1 should not be empty,and length must for 2 to 20 between!^前台显示登录名不能为空,长度必须在 2 和 20之间
//    MSG_500402,//500402^PlatformId should not be empty,and length must for 1 to 10 between!^游戏平台ID不能为空,长度必须在 1 和 10之间
//    MSG_500205,//500205^Billno should not be empty,and length must for 1 to 16 between!^注单号不能为空,长度必须在 1 和 16之间
//    MSG_500209,//500209^WinSequence should not be empty,and length must for 1 to 3 between!^连赢序号不能为空,长度必须在 1 和 3之间
//    MSG_500211,//500211^Gmcode should not be empty,and length must for 1 to 14 between!^游戏局号不能为空,长度必须在 1 和 14之间
//    MSG_500212,//500212^Gametype should not be empty,and length must for 1 to 6 between!^游戏类型不能为空,长度必须在 1 和 6之间
//    MSG_500221,//500221^WinFlag should not be empty,and length must for 1 to 12 between!^记录是否为同一次连赢(连赢次数)不能为空,长度必须在 1 和 12之间
//    MSG_402102,//402102^ProductName should not be empty,and length must for 3 to 10 between!^产品名不能为空,长度必须在 3 和 10之间
//    MSG_304608,//304608^ProcessBy should not be empty,and length must for 2 to 20 between!^转账确认人不能为空,长度必须在 2 和 20之间
//    MSG_304607,//304607^ReturnCode should not be empty,and length must for 1 to 20 between!^转账返回码不能为空,长度必须在 1 和 20之间
//    MSG_304511,//304511^Source should not be empty,and length must for 1 to 20 between!^转出源不能为空,长度必须在 1 和 20之间
//    MSG_304512,//304512^Target should not be empty,and length must for 1 to 20 between!^接收地不能为空,长度必须在 1 和 20之间
//    MSG_310611,//310611^Idcard should not be empty,and length must for 8 to 30 between!^会员证件号不能为空,长度必须在 8 和 30之间
//    MSG_503212,//503212^GameId should not be empty,and length must for 2 to 20 between!^游戏ID不能为空,长度必须在 2 和 20之间
//    MSG_506208,//506208^SendContent should not be empty,and length must for 1 to 70 between!^发送内容不能为空,长度必须在 1 和 70之间
//    MSG_314003,//314003^BtcUrl should not be empty,and length must for 2 to 180 between!^比特币验证地址不能为空,长度必须在 2 和 180之间
//    MSG_314002,//314002^BtcName should not be empty,and length must for 2 to 50 between!^比特币名称不能为空,长度必须在 2 和 50之间
//    MSG_314004,//314004^ConfirmBy should not be empty,and length must for 2 to 20 between!^转账确认人不能为空,长度必须在 2 和 20之间
//    MSG_314005,//314005^Phone should not be empty,and length must for 8 to 16 between!^会员电话不能为空,长度必须在 8 和 16之间
//    MSG_314006,//314006^DomainName should not be empty,and length must for 1 to 40 between!^域名不能为空,长度必须在 1 和 40之间
//    MSG_320003,//320003^Currency Type should not be empty,and length must for 2 to 5 between!^货币种类不能为空,长度必须在 2 和 5之间
//    MSG_304306,//304306^ApproveBy or Backuper should not be empty,and length must for 1 to 30 between!^审批人或备份人不能为空,长度必须在 1 和 30之间
//    MSG_304307,//304307^Playtype should not be empty,and length must for 1 to 4 between!^游戏玩法不能为空,长度必须在 1 和 4之间
//    MSG_304309,//304308^Bind LoginName should not be empty,and length must for 2 to 20 between!^绑定登录名不能为空,长度必须在 2 和 20之间
//    MSG_402208,//402208^Customer Level must be a positive integer, the length is more than 1!^客户等级必须是一个正整数,长度不超过1
//    MSG_502291,//52291^taskStartTime must be small than taskEndTime^任务开始时间必需小於任务结束时间!
//    MSG_502292,//52291^taskEndTime cannot be small than now^任务结束时间不能小於当前时间!
//    MSG_502293,//52291^conditionStartTime must be small than conditionEndTime^条件开始时间必需小於条件结束时间!
//    MSG_319998,//319998^Promotion Pre has expired^该优惠预审已过期
//    MSG_301405,//301405^Date Range must specify a period of time, format for (1d,2w,3m,4y)!^时间跨度必须指定一段时间,格式为(1d,2w,3m,4y)
//    MSG_211102,//211102^ReportMonth not correct format,must be yyyy-MM!^报表月份格式不正确,格式必须为yyyy-MM!
//    MSG_825022,//825022^Month not correct format,must be yyyy-mm^月份格式不正确,格式必须为yyyy-MM
//    MSG_201412,//201412^Total Deposit must be greater than zero,format must be **.000000!^总存款金额必须大于0,格式必须为**.000000
//    MSG_201413,//201413^Total Withdraw must be greater than zero,format must be **.000000!^总取款金额必须大于0,格式必须为**.000000
//    MSG_201414,//201414^Commission Charge must be greater than zero,format must be **.000000!^贡献值变更金额必须大于0,格式必须为**.000000
//    MSG_201415,//201418^TotalModifyCreditAdd must be greater than zero,format must be **.000000!^额度变更增加金额必须大于0,格式必须为**.000000
//    MSG_201411,//201419^TotalModifyCreditSub must be greater than zero,format must be **.000000!^额度变更减少金额必须大于0,格式必须为**.000000
//    MSG_306912,//306912^RATE not correct format,must be **.00!^洗码佣金比率格式不对，必须为**.00
//    MSG_822010,//822010^ProLostWin not correct format,must be Number(22,6)!^输赢度数格式不正确,格式必须为Number(22,6)
//    MSG_822011,//822011^ProTotalLostWin not correct format,must be Number(22,6)!^优惠总输赢度格式不正确,格式必须为Number(22,6)
//    MSG_822012,//822012^ProTotalDeposit not correct format,must be Number(22,6)!^优惠总存款格式不正确,格式必须为Number(22,6)
//    MSG_822013,//822013^ProTotalWithdrawl not correct format,must be Number(22,6)!^优惠总取款格式不正确,格式必须为Number(22,6)
//    MSG_822014,//822014^ProApplyAmount not correct format,must be Number(22,6)!^优惠申请金额格式不正确,格式必须为Number(22,6)
//    MSG_201307,//201307^CustomerContribution not correct format,must be **.000000!^客户贡献值格式不正确,格式必须为**.000000!
//    MSG_201308,//201308^AgentCommission not correct format,must be **.000000!^代理商佣金格式不正确,格式必须为**.000000!
//    MSG_201309,//201309^Commission Rate not correct format,must be **.00!^佣金比例格式不正确,格式必须为**.00!
//    MSG_201405,//201405^Before Credit should not be empty,and format must be **.000000!^上次额度不能为空且格式必须为**.000000!
//    MSG_201406,//201406^Game Credit should not be empty,and format must be **.000000!^游戏额度不能为空且格式必须为**.000000!
//    MSG_201407,//201407^Local Credit should not be empty,and format must be **.000000!^本地额度不能为空且格式必须为**.000000!
//    MSG_201408,//201408^Total Credit should not be empty,and format must be **.000000!^总额度不能为空且格式必须为**.000000!
//    MSG_201409,//201409^Credit Change should not be empty,and format must be **.000000!^额度变更不能为空且格式必须为**.000000!
//    MSG_201410,//201410^Total Contribution should not be empty,and format must be **.000000!^总贡献值不能为空且格式必须为**.000000!
//    MSG_206011,//206011^Contribution Limit not correct format,must be **.000000!^贡献额度格式不正确,格式必须为**.000000!
//    MSG_311614,//311614^Amount should be greater than zero!^金额必须大于0
//    MSG_301617,//301617^BtcAmount must be greater than zero,format must be**.00000000!^比特币额度必须大于0,格式必须为**.00000000!
//    MSG_301618,//301618^BtcRate must be greater than zero,format must be**.00000000!^比特币汇率必须大于0,格式必须为**.00000000!
//    MSG_201502,//201502^RATE must be greater than zero,format must be **。00!^洗码比必须大于0,格式必须为 **.00
//    MSG_300204,//300204^Percentage not correct format,must be **.00!^百分比格式不正确,格式必须为**.00
//    MSG_300205,//300205^Max Amount not correct format,must be **.00!^最大金额格式不正确,格式必须为**.00
//    MSG_201734,//201734^Email Format is wrong^邮箱格式错误
//    MSG_205310,//205310^deducted integral is not greater than customer's integral!^扣除积分不能超过会员本地积分!
//    MSG_204668,//204668^This Stock Value already existed^此月股票已存在
//    MSG_202336,//202336^Number of Login Name can not greater than 1000^传入登录名数量不能大于1000
//    MSG_300300,//300300^Number of phone can not greater than 1000^传入电话数量不能大于1000
//    MSG_300301,//300301^Number of bankNo can not greater than 1000^传入银行卡号数量不能大于1000
//    MSG_300302,//300302^Number of realName can not greater than 1000^传入真实名字数量不能大于1000
//    MSG_307023,//307023^This Phone has been activated,can not activated again^该电话已经被激活，不能重复激活
//    MSG_305159,//305159^Source Customer has unfinished account exchange request^转出账户有未完成的额度兑换提案
//    MSG_305150,//305150^Create credit exchange request failed^创建额度兑换提案失败
//    MSG_305151,//305150^Create withdraw request failed^创建取款提案失败
//    MSG_201401,//201411^Contribution Day not correct format,must be yyyy-MM-dd!^贡献时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_200204,//200204^ContributionMonthBegin not correct format,must be yyyy-MM-dd!^佣金月份起格式不正确,格式必须为yyyy-MM-dd
//    MSG_200205,//200205^ContributionMonthEnd not correct format,must be yyyy-MM-dd!^佣金月份止格式不正确,格式必须为yyyy-MM-dd
//    MSG_200802,//200802^ContributionMonth not correct format,must be yyyy-MM-dd!^佣金月份格式不正确,格式必须为yyyy-MM-dd!
//    MSG_610202,//610202^SumDateBegin not correct format,must be yyyy-MM-dd!^时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_610203,//610203^SumDateEnd not correct format,must be yyyy-MM-dd!^时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_206104,//206104^Contribution Month should not be empty,and format must be yyyy-MM-dd!^佣金月份不能为空且格式必须为yyyy-MM-dd
//    MSG_202288,//202288^BirthDate not correct format,must be yyyy-mm-dd!^生日格式必须为yyyy-mm-dd！
//    MSG_201801,//201801^CommissionDate should not be empty,and format must be yyyy-MM-dd!^佣金时间不能为空且格式必须为yyyy-MM-dd
//    MSG_337703,//337703^DimensionValue shouldn't be empty,format must YYYY-MM!^统计天不能为空,格式必须为 YYYY-MM
//    MSG_337702,//337702^DimensionValue shouldn't be empty,format must YYYY-MM-DD!^统计天不能为空,格式必须为 YYYY-MM-DD
//    MSG_206103,//206105^Format of ContributionMonth not correct,must be yyyy-MM-dd 00:00:00^佣金月份时间格式不正确,必须为 yyyy-MM-dd 00:00:00
//    MSG_201709,//201708^Email not correct format,must be **@***.***!^会员邮箱格式不正确,格式必须为**@***.***！
//    MSG_309319,//309319^New Agent can not Himself!^该新代理不能是他自己
//    MSG_310612,//310612^Remarks should not be empty,length must for  1 to 200 between!^备注长度必须在1和200之间
//    MSG_811001,//811001^siteId, length must for 2 to 99 between!^siteId长度必须在2和99之间
//    MSG_201707,//201709^Email should not be empty, length must for 6 to 50 between!^邮箱长度必须在6和50之间
//    MSG_500902,//500902^WSWinConsecutiveLogs ID length must for 2 to  22 between!^连赢记录日志ID长度必须在2和22之间
//    MSG_500802,//500802^TActivityPrizeId ID length must for 2 to  22 between!^奖品ID长度必须在1和200之间
//    MSG_502202,//502202^WSActivityEurocupGuess ID length must for 2 to  22 between!^竞猜ID长度必须在2和22之间
//    MSG_307305,//307305^Remarks should not be empty,length must for 1 to 30 between!^备注长度必须在1和30之间
//    MSG_307306,//307306^Remarks should not be empty,length must for 1 to 100 between!^备注长度必须在1和100之间
//    MSG_310503,//310503^DRequestId length must for 1 to 19 between!^存款提案ID长度必须在1和19之间
//    MSG_310504,//310504^PRequestId length must for 1 to 19 between!^优惠提案ID长度必须在1和19之间
//    MSG_502801,//502801^WSActivityMatch should not be empty!^WSActivityMatch不能为空!
//    MSG_600301,//600301^WSQueryCustAnalysis should not be empty!^客户统计查询不能为空!
//    MSG_600302,//600302^BeginDate should not be empty!^统计开始时间不能为空!
//    MSG_600303,//600303^EndDate should not be empty!^统计结束时间不能为空!
//    MSG_206601,//206601^WSCustomers should not be empty!^会员信息不能为空!
//    MSG_532101,//532101^edType should not be empty!^红包类型不能为空!
//    MSG_532102,//532102^dispatchRule should not be empty!^分派规则不能为空!
//    MSG_532103,//532103^limitNumber should not be empty!^限制领取人数不能为空!
//    MSG_532104,//532104^receivePassword should not be empty!^领取红包口令不能为空!
//    MSG_532105,//532105^redRemark should not be empty!^红包备注信息不能为空!
//    MSG_532106,//532106^requestId should not be empty!^红包提案号码不能为空!
//    MSG_667004,//667004^loginName should not be empty!^用户名不能为空!
//    MSG_667005,//667005^amount should not be empty!^红包金额不能为空!
//    MSG_667006,//667006^requestId should not be empty!^提案Id不能为空!
//    MSG_667007,//667007^recordType should not be empty!^红包类型不能为空!
//    MSG_667008,//667008^receiveStatus should not be empty!^领取状态不能为空!
//    MSG_667009,//667009^overdueStatus should not be empty!^过期状态不能为空!
//    MSG_667010,//667010^overdueDate should not be empty!^过期时间不能为空!
//    MSG_503200,//503200^WSBettingReports should not be empty!^WSBettingReports不能为空!
//    MSG_700401,//700401^List<WSTMPhoneDetail> should not be empty!^WList<WSTMPhoneDetail>不能为空!
//    MSG_700402,//700402^tmPhoneMasterId should not be empty!^tmPhoneMasterId不能为空!
//    MSG_500201,//500201^WSActivityWinConsecutive should not be empty!^WSActivityWinConsecutive不能为空!
//    MSG_200301,//200301^WSCustomersAgentContract should not be empty!^佣金方案不能为空!
//    MSG_632101,//632101^pkId should not be empty!^红包主键不能为空!
//    MSG_666004,//666004^timeBegin should not be empty!^起始时间不能为空!
//    MSG_666005,//666005^timeEnd should not be empty!^截止时间不能为空!
//    MSG_666013,//666013^receivePassword should not be empty!^领取口令不能为空!
//    MSG_202200,//202201^wsRebateConfig should not be empty!^会员游戏洗码配置信息不能为空!
//    MSG_600201,//600201^WSQueryCustAnalysis should not be empty!^客户统计查询不能为空!
//    MSG_502001,//502001^WSActivityEurocupGuess should not be empty!^WSActivityEurocupGuess不能为空!
//    MSG_700801,//700801^WSTMPhoneMaster should not be empty!^WSTMPhoneMaster不能为空!
//    MSG_600304,//600304^CankaoBeginDate should not be empty!^参考开始时间不能为空!
//    MSG_502701,//502701^WSActivityMatch should not be empty!^WSActivityMatch不能为空!
//    MSG_600305,//600305^CankaoEndDate should not be empty!^参考结束时间 不能为空!
//    MSG_804001,//804001^vo should not be empty!^vo 不能为空!
//    MSG_804002,//804002^customerId should not be empty!^customerId 不能为空!
//    MSG_804004,//804004^customer not exist or is disable!^客户不存在或已失效
//    MSG_804006,//804006^IP should not be empty!^IP 不能为空!
//    MSG_304601,//304601^WSTransferProcess should not be empty!^转帐确认信息 不能为空!
//    MSG_504100,//504100^WSActivityConstant should not be empty!^WSActivityConstant 不能为空!
//    MSG_504102,//504102^Analyst should not be empty!^分析师 不能为空!
//    MSG_504103,//504103^BallType should not be empty!^球类 不能为空!
//    MSG_504106,//504106^MatchTime should not be empty!^赛事时间 不能为空!
//    MSG_504107,//504107^Team should not be empty!^推荐队伍 不能为空!
//    MSG_504108,//504108^MatchType should not be empty!^赛事类型 不能为空!
//    MSG_504109,//504109^Content should not be empty!^推荐内容 不能为空!
//    MSG_504110,//504110^CustLevel should not be empty!^会员级别 不能为空!
//    MSG_504111,//504111^Record should not be empty!^推荐战绩 不能为空!
//    MSG_504112,//504112^AnalysisLevel should not be empty!^推荐级别 不能为空!
//    MSG_504113,//504113^AnalystUrl should not be empty!^推荐师URL 不能为空!
//    MSG_504114,//504114^Status should not be empty!^状态 不能为空!
//    MSG_700701,//700701^WSCallBackInfo should not be empty!^WSCallBackInfo 不能为空!
//    MSG_700705,//700705^uuId should not be empty!^uuId 不能为空!
//    MSG_314001,//314001^WSDepositInsure should not be empty!^WSDepositInsure 不能为空!
//    MSG_347001,//347001^WSTransferConfirm should not be empty!^转帐确认信息 不能为空!
//    MSG_805001,//805001^Request should not be empty!^参数 不能为空!
//    MSG_805005,//805005^IpAddress should not be empty!^IP地址 不能为空!
//    MSG_506801,//506801^WSActivityPrizeNum should not be empty!^WSActivityPrizeNum 不能为空!
//    MSG_502802,//502802^WSActivityMatchRank should not be empty!^WSActivityMatchRank 不能为空!
//    MSG_200201,//200201^WSQueryAgentCommission should not be empty!^佣金记录查询参数 不能为空!
//    MSG_334852,//334852^Transfer out amount should not be empty!^转出金额 不能为空!
//    MSG_504001,//504001^Constant ID should not be empty!^常量ID 不能为空!
//    MSG_504301,//504301^ID should not be empty!^ID 不能为空!
//    MSG_504302,//504302^ids should not be empty!^ids 不能为空!
//    MSG_500901,//500901^WSWinConsecutiveLogs should not be empty!^WSWinConsecutiveLogs 不能为空!
//    MSG_502901,//502901^WSActivityMatchLogs should not be empty!^WSActivityMatchLogs 不能为空!
//    MSG_206100,//206104^Contribution Month should not be empty!^佣金月份 不能为空!
//    MSG_504200,//504200^WSActivityRecommend should not be empty!^WSActivityRecommend 不能为空!
//    MSG_203502,//203502^AgentName should not be empty!^代理登录名 不能为空!
//    MSG_521001,//521001^WSBrowserVersion should not be empty!^WSBrowserVersion 不能为空!
//    MSG_521002,//521002^VersionNum should not be empty!^版本号 不能为空!
//    MSG_521003,//521003^Domain should not be empty!^域名 不能为空!
//    MSG_206611,//206611^BTC-OFFON should not be empty!^交易关闭 不能为空!
//    MSG_300831,//300831^Update Email tokenCode should not be empty!^Update Email tokenCode 不能为空!
//    MSG_304301,//304301^WSRequestsApprove should not be empty!^审批对象不能为空!
//    MSG_804009,//804009^TokenCode should not be empty!^登录TOKEN不能为空 不能为空!
//    MSG_310601,//310601^WSModifyIdcardRequests should not be empty!^WSModifyIdcardRequests 不能为空!
//    MSG_317001,//317001^WSAgentCommissionNew should not be empty!^WSAgentCommissionNew 不能为空!
//    MSG_317002,//317002^AgentCommission should not be empty!^代理佣金 不能为空!
//    MSG_317003,//317003^ContributionType should not be empty!^佣金类型 不能为空!
//    MSG_317004,//317004^TotalCustNum should not be empty!^所有有效下线会员数 不能为空!
//    MSG_317005,//317005^IsStandard should not be empty!^本月是否达标 不能为空!
//    MSG_317006,//317006^TotalContribution should not be empty!^下线有效投注总额 不能为空!
//    MSG_206099,//206107^Contribution Month should not be empty!^佣金月份 不能为空!
//    MSG_206108,//206108^Start Date should not be empty!^佣金开始时间 不能为空!
//    MSG_206109,//206109^End Date should not be empty!^佣金结束时间 不能为空!
//    MSG_506201,//506201^sendSMS should not be empty!^sendSMS 不能为空!
//    MSG_200315,//200315^LastUpdatedBy should not be empty!^修改人 不能为空!
//    MSG_301001,//301001^WSRequestsEmailOrSmsStatus should not be empty!^提案邮件短信状态 不能为空!
//    MSG_201400,//201401^WSCustomerPoint should not be empty!^用户积分对象 不能为空!
//    MSG_201601,//201601^WSGameCredit should not be empty!^会员游戏额度 不能为空!
//    MSG_240111,//240111^AgentContract should not be empty!^合作伙伴佣金方案 不能为空!
//    MSG_240116,//240116^ModifydBy should not be empty!^会员状态 不能为空!
//    MSG_240122,//240122^IsMarket should not be empty!^是否发展下线 不能为空!
//    MSG_240118,//240118^AgentContractId should not be empty!^方案ID 不能为空!
//    MSG_506501,//506501^WSExchangeRate should not be empty!^汇率信息 不能为空!
//    MSG_506502,//506502^ExchangeRate should not be empty!^汇率 不能为空!
//    MSG_506503,//506503^Currency should not be empty!^币种 不能为空!
//    MSG_203728,//203728^QueryCustomers should not be empty!^查询条件不能为空 不能为空!
//    MSG_319901,//319901^WSModifyContractRequests should not be empty!^修改代理佣金方案提案 不能为空!
//    MSG_305301,//305301^WSWithdrawalRequests should not be empty!^取款提案信息 不能为空!
//    MSG_304901,//304901^WSXMRequests should not be empty!^洗码提案信息 不能为空!
//    MSG_200307,//200307^modifyContractRequest should not be empty!^修改佣金方案提案 不能为空!
//    MSG_304310,//304310^Newflag should not be empty!^提案新状态 不能为空!
//    MSG_310401,//310401^WSLotterys should not be empty!^奖卷对象 不能为空!
//    MSG_700709,//700709^QueryCallBackInfoRequest should not be empty!^QueryCallBackInfoRequest 不能为空!
//    MSG_500401,//500401^WSActivityGenerateSpecialPrizes should not be empty!^WSActivityGenerateSpecialPrizes 不能为空!
//    MSG_503805,//503805^Customer ID and Parent ID Cannot at the same time is empty! ^ParentId和CustomerId不能同时为空!
//    MSG_700702,//700702^callManage1Id should not be empty!^callManage1Id 不能为空!
//    MSG_700703,//700703^dealBy should not be empty!^处理人 不能为空!
//    MSG_310901,//310901^WSTransfer should not be empty!^WSTransfer 不能为空!
//    MSG_801002,//801002^Pwd should not be empty!^登录密码 不能为空!
//    MSG_506200,//506202^verifyCode should not be empty!^verifyCode 不能为空!
//    MSG_201721,//201721^Signature should not be empty!^签名档 不能为空!
//    MSG_361005,//361005^openId should not be empty!^openId 不能为空!
//    MSG_361004,//361006^loginEndPointType should not be empty!^loginEndPointType 不能为空!
//    MSG_361002,//361002^ThirdLoginName should not be empty!^ThirdLoginName 不能为空!
//    MSG_801003,//801003^phone should not be empty!^手机号 不能为空!
//    MSG_346007,//346007^EndpointType should not be empty!^终端类型 不能为空!
//    MSG_346008,//346008^DomainName should not be empty!^来源域名 不能为空!
//    MSG_346002,//346002^SourceBillno should not be empty!^对方转账单号 不能为空!
//    MSG_346012,//346012^CreatedBy should not be empty!^创建人 不能为空!
//    MSG_346116,//346116^remarks should not be empty!^备注 不能为空!
//    MSG_200001,//200001^Bill No already exists!^定单号重复!
//    MSG_301775,//301775^Flow time format is incorrect!param:{0}^优惠流水日期格式不正确!param:{0}
//    MSG_211110,//211110^BeginDateMonth not correct format,must be  yyyy-MM-dd HH:mm:ss!^月开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_211111,//211111^EndDateMonth not correct format,must be  yyyy-MM-dd HH:mm:ss!^月结束时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_211112,//211112^BeginDateWeek not correct format,must be  yyyy-MM-dd HH:mm:ss!^周开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_211113,//211113^EndDateWeek not correct format,must be  yyyy-MM-dd HH:mm:ss!^周结束时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_827002,//827002^ExcuteTime not correct format,must be yyyy-MM-dd HH:mm:ss!^执行时间格式不对，必须为yyyy-MM-dd HH:mm:ss!
//    MSG_305001,//305001^CreatedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^创建时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_305002,//305002^CreatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^创建时间止格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_824020,//824020^AppointmentDate not correct format,must be yyyy-MM-dd HH:mm:ss!^预约日期格式不对，必须为yyyy-MM-dd HH:mm:ss!
//    MSG_300009,//300009^DepositDate not correct format,must be yyyy-MM-dd HH:mm:ss!^汇款日期格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_300010,//300010^BeginTime not correct format,must be yyyy-MM-dd HH:mm:ss!^开始时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss！
//    MSG_300115,//300011^endTime not correct format,must be yyyy-MM-dd HH:mm:ss!^endTime格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_300114,//300013^Format of Bet Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^投注时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_300113,//300014^Format of ExpiryDate not correct,must be yyyy-MM-dd 00:00:00^公告逾期时间格式不正确,必须为 yyyy-MM-dd 00:00:00
//    MSG_300112,//300015^Format of EffectivityDate not correct,must be yyyy-MM-dd 00:00:00^公告有效时间格式不正确,必须为 yyyy-MM-dd 00:00:00
//    MSG_300111,//300016^Format of Job Begin Time not correct,must be yyyy-MM-dd HH:mm:ss^Job开始时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_300110,//300017^EffectDateStart not correct format,must be yyyy-MM-dd HH:mm:ss!^EffectDateStart 格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_300109,//300018^ExpiredDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^ExpiredDateEnd 格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_300108,//300019^DueBeginDate not correct format,must be yyyy-MM-dd HH:mm:ss!^任务开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_300107,//300020^DueEndDate not correct format,must be yyyy-MM-dd HH:mm:ss!^任务结束时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_300106,//300021^LastUpdateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^最后修改时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss！
//    MSG_300105,//300023^applyDate not correct format,must be yyyy-MM-dd HH:mm:ss!^申请回拨时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss！
//    MSG_300104,//300024^EndDateBegin not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间起 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300103,//300025^EndDateEnd not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间止 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300102,//300026^StartDateBegin not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码开始时间起格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300101,//300027^StartDateEnd not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间止 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300028,//300028^StartDate not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码开始时间 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300100,//300029^EndDate not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_300030,//300030^BirthDate not correct format,must be yyyy-MM-dd HH:mm:ss!^格式不正确,格式必须为yyyy-MM-dd HH:mm:ss！
//    MSG_202173,//202173^GameKey must to lowercase letters and Numbers composition!^游戏密码必须为小写字母和数字组成！
//    MSG_202174,//202173^Withdrawal password must to  letters and Numbers composition!^取款密码必须为字母和数字组成！
//    MSG_202222,//202222^Invalid format!^格式错误！
//    MSG_201911,//201911^This domain has already assign!^此域名已配置
//    MSG_505601,//505601^taskType is wrong^任务类型错误
//    MSG_308001,//308001^Virtual Account disabled for this action^虚拟币账户不能进行该操作
//    MSG_505303,//505303^AnnouncementId Does not existed^公告信息不存在
//    MSG_201304,//201304^Try account can't operate!^试玩会员不能进行该操作
//    MSG_201306,//201306^AgentId is not parentId of the customer!^代理商ID不是会员的父ID
//    MSG_206004,//206004^Agent LoginName account[ {0} ] is disable!^代理账号[ {0} ]状态为禁用
//    MSG_201510,//201510^CUSTOMER_ID,GAME_ID,GAME_KIND Add data may not be repeated!^根据CUSTOMER_ID,GAME_ID,GAME_KIND 添加的数据不能重复
//    MSG_208006,//208006^The phone has been in existence !^此电话已被其他账号绑定
//    MSG_301706,//301706^Login name only supports uppercase and lowercase numbers！^登录名仅支持大小写字母
//    MSG_301707,//301706^Store code does not exist！^store不存在
//    MSG_301708,//301706^firstName Only English case and numbers are supported！^firstName仅支持英文字母
//    MSG_301709,//301706^middleName Only English case and numbers are supported！^middleName仅支持英文字母
//    MSG_301710,//301706^lastName Only English case and numbers are supported！^lastName仅支持英文字母
//    MSG_831003,//831003^Login Name Does Not Existed^客户登录名不存在
//    MSG_201714,//201713^Login Name already existed^会员登录名已存在!
//    MSG_305866,//305866^Prefix of loginName does not correct {0}^登录名前缀不正确,必须为 {0}
//    MSG_201719,//201722^Phone can't repeat!^很抱歉,该电话已被注册,请联系客服,谢谢！
//    MSG_201305,//201305^RelationValue is not correct fomart!^域名格式不正确
//    MSG_334924,//334924^Length of Remarks must between 1 and 1200^备注长度必须在1到1200之间
//    MSG_304006,//304006^Login Name must between 1 and 20^登录名只能为1到20
//    MSG_403406,//403406^Length of Remarks must between 1 and 600^备注长度必须在1到600之间
//    MSG_201733,//201733^Length of Eamil must between 6 and 50^邮箱地址长度必须在6到50之间
//    MSG_201731,//201731^Length of Phone must between 8 and 16^会员电话长度必须在8到16之间
//    MSG_201704,//201704^Address should not be empty,and length must between 0 to 50.^会员地址不能为空,长度必须在0和50之间.
//    MSG_201724,//201724^Remarks should not be empty,and length must between 1 to 200.^备注不能为空,长度必须在1和200之间.
//    MSG_503403,//503403^Remarks length must for 1 to 63 between!^备注 长度必须在1和63之间!
//    MSG_301311,//301311^Length of Phone must between 8 and 16^会员电话长度必须在8到16之间
//    MSG_300890,//300890^Priority Order must between 0 and 99^银行卡优先级的值只能为0到99
//    MSG_300800,//300800^BankAlias length must for 1 to 200 between!^银行别名长度必须在 1和 200之间
//    MSG_200710,//200710^Login Name must between 1 and 20^登录名只能为1到20
//    MSG_503103,//503103^Length of Page title must between 1 and 100^页面标题长度必须在1到100之间
//    MSG_503104,//503103^Length of Message title must between 1 and 100^公告标题长度必须在1到100之间
//    MSG_503507,//503507^Content should not be empty,and length must for 1 to 4000 between!^公告内容不能为空,长度必须在 1和 4000之间
//    MSG_503508,//503508^Page title should not be empty,and length must for 1 to 100 between!^页面标题不能为空,长度必须在 1和 100之间
//    MSG_503509,//503509^Message title should not be empty,and length must for 1 to 100 between!^公告标题不能为空,长度必须在 1和 100之间
//    MSG_505107,//505107^Content should not be empty,and length must for 1 to 100 between!^公告内容不能为空,长度必须在 1和 100之间
//    MSG_505108,//505108^Link should not be empty,and length must for 1 to 30 between!^链接地址不能为空,长度必须在 1和 30之间
//    MSG_821013,//821013^Length of taskCondition  must between 1 and 3000^任务条件长度必须在1到3000之间
//    MSG_504807,//504807^Length of Content must between 1 and 400^公告内容长度必须在1到400之间
//    MSG_366116,//366116^remarks length must for 1 to 320 between!^备注长度必须在 1 和 320 之间！
//    MSG_202302,//202302^DomainName length must for 1 to 50 between!^开户域名长度必须在 1和50 之间！
//    MSG_300529,//300529^ArtifactRemark length must for 1 to 20 between!^人工备注长度必须在1和20之间
//    MSG_300531,//300531^End Point Url length must for 1 to 50 between!^提交端URL长度必须在1和50之间
//    MSG_301720,//301720^Reserve1 length must for 1 to 200 between!^Reserve1必须在1和20之间
//    MSG_300434,//300434^RemittanceRemarks length must for 1 to 30 between!^汇款备注长度必须在1和30之间
//    MSG_300437,//300437^End Point Url length must for 1 to 50 between!^提交端URL长度必须在1和50之间
//    MSG_300435,//300434^RemittanceRemarks length must for 1 to 30 between!^汇款备注长度必须在1和30之间
//    MSG_300408,//300408^BankAccountNo should not be empty,and length must for 6 to 500 between!^银行账号不能为空,长度必须在 6和 500之间
//    MSG_100024,//100024^RemittanceAccountNo should not be empty,and length must for 10 to 200 between!^汇款卡号不能为空,长度必须在 10和 200之间
//    MSG_300438,//300438^DepositurationId length must for 1 to 20 between!^交易记录流水编号长度必须在1和20之间
//    MSG_315006,//315006^Length Of Bond Context can not greater than 100^绑定内容长度不能超过100
//    MSG_300906,//300906^Currency should not be empty,and length must for 2 to 5 between!^货币种类不能为空,长度必须在2和5之间
//    MSG_300227,//300227^ApproveRole length must for 1 to 200 between!^审批角色长度必须在1和200之间
//    MSG_300228,//300228^PromotionGroup length must for 1 to 150 between!^优惠分组名长度必须在1和150之间
//    MSG_202165,//202165^Logged into the game, Last game should be empty!^已登录过游戏,最后登录游戏必须为空！
//    MSG_201753,//201753^RealName format is wrong^真实姓名必须以中文或大写字母组成
//    MSG_201711,//201712^Login name must to lowercase letters and Numbers composition.^登录名必须为小写字母和数字组成.
//    MSG_201732,//201732^Format of Phone is wrong^会员电话格式不对
//    MSG_302913,//302913^xm request  rate is not equals with the rate witch is load from db.^提案洗码比例和后端校验的比例不一致.
//    MSG_334946,//334946^Loan or Return amounr must equals Amount^借出或者归还金额必须等于客户信用额度
//    MSG_201725,//201726^One day, the same ip can only create {0} trial customers^同IP每日只限开启 {0} 个试玩账号
//    MSG_201717,//201726^one day,the same ip only create {0} customers^同一天内,同一个IP只能开 {0} 个同类会员
//    MSG_381039,//381039^You have already registered multiple accounts^X天内,同一个IP只能开 X 个同类会员
//    MSG_381040,//381039^You have already registered multiple accounts^X天内,同一个IP只能开 X 个同类会员
//    MSG_381041,//381039^You have already registered multiple accounts^X天内,同一个IP只能开 X 个同类会员
//    MSG_308578,//308578^Withdrawal conversion rate cannot be less than or equal to 0^取款转换汇率不能小于等于0
//    MSG_304845,//304845^BTC Amount must greater than 0^BTC取款金额不能小于0
//    MSG_304846,//304846^Rate of BTC to CNY must be number format's string and greater than 0 and should like **.00^BTC to CNY 兑换只能是大于0的数字,最多8位小数
//    MSG_509318,//509318^PhoneBy not correct format,must be number!^会员电话格式不正确,格式必须为number
//    MSG_301616,//301617^BtcAmount must be greater than zero,format must be **.00000000!^比特币额度必须大于0,格式必须为**.00000000!
//    MSG_301615,//301618^BtcRate  must be greater than zero,format must be **.00000000!^比特币汇率必须大于0,格式必须为**.00000000!
//    MSG_202148,//202148^Phone not exist or is disable!^绑定电话 不存在或已失效！
//    MSG_202228,//202228^AP bank account verify failed!^银行卡校验失败，银行卡长度为空！
//    MSG_523157,//523157^no matched data^没有找到匹配的数据
//    MSG_202103,//202103^Phone not bind account!^该手机号未绑定帐号
//    MSG_309610,//309610^phone can't repeat!^申请回拨电话不能重复!
//    MSG_700111,//700111^wrong verify code or already used!^验证码错误或己经使用!
//    MSG_303611,//303611^Request flag must be 0^提案状态必须为0
//    MSG_301216,//301216^parent must update!^会员上线必须改变!
//    MSG_301217,//301217^customers flag must be enabled!^会员状态必须为启用!
//    MSG_301218,//301218^Try account can't the operator!^试玩会员不能进行该操作!
//    MSG_301219,//301219^Parent Can't is oneself!^上线会员不能是自己!
//    MSG_301220,//301220^AMTC Customers can't deposit!^AMTC 用户不能存款!
//    MSG_301221,//301221^Record flag can't be deleted!^记录状态不能被删除!
//    MSG_301222,//301222^wrong verify code or already used!^验证码错误或己经使用!
//    MSG_301223,//301223^Login Flag can not same^传入激活状态与已有的激活状态相同!
//    MSG_301224,//301224^Old Phone and Phone can not be same^新旧电话号码不能相同!
//    MSG_301225,//301225^AccountName flag must be enabled!^推荐人状态必须为启用!
//    MSG_301226,//301226^Try account can't the operator!^推荐人是试玩账号不能进行该操作!
//    MSG_301227,//301227^AccountNameBy flag must be enabled!^被推荐人状态必须为启用!
//    MSG_301228,//301228^Try account can't the operator!^被推荐人是试玩账号不能进行该操作!
//    MSG_301229,//301229^AccountName can't match with accountNameBy!^被推荐人与被推荐人不能为同一个人!
//    MSG_301230,//301230^Unable to Reject email Request!^不能驳回这个邮箱提案!
//    MSG_301231,//301231^Unable to Approve email Request!^不能审批这个邮箱提案!
//    MSG_301232,//301232^Unable to Audit email Request!^不能审核这个邮箱提案!
//    MSG_301233,//301233^customers must not is promotion account!^会员不能是优惠系统会员!
//    MSG_301234,//301234^Promotion flag must be enabled!^优惠状态必须为启用!
//    MSG_301235,//301235^The IP:{0} has already applied this promotion!^当前IP{0}已经领过此优惠!
//    MSG_301236,//301236^The phone has already applied this promotion!^当前手机号已经领过此优惠!
//    MSG_301237,//301237^The customer realName has already applied this promotion!^当前客户真实姓名已经领过此优惠!
//    MSG_301238,//301238^he bank card has already applied this promotion!^当前银行卡已经领过此优惠!
//    MSG_301240,//301240^xm flag must be enabled^洗码状态必须为启用!
//    MSG_301241,//301241^AMTC Agent can't transfer credit!^AMTC 代理不能转账!
//    MSG_301242,//301242^Source Account must be enabled!^转出账号状态必须为启用!
//    MSG_301243,//301243^Target Account must be enabled!^转入账号状态必须为启用!
//    MSG_301244,//301244^Unable to process request due to bank information match failed!^银改提案信息匹配失败,无法处理取款请求!
//    MSG_301245,//301245^red envelope had forbiddened!^红包功能已被禁用!
//    MSG_301246,//301246^tmPhoneMasterId can not be 0^tmPhoneMasterId不能为0!
//    MSG_301247,//301247^The Records has been deleted!^转账记录已经删除!
//    MSG_301248,//301248^Yeb has already transferred out,can not loan credit line^过夜利息已经转出,无法进行借出/归还操作!
//    MSG_301249,//301249^Yeb has already transfer out,can not loan credit line^过夜利息以及转出,无法进行借出/归还操作审批!
//    MSG_301250,//301250^Could not update credit. Customer Type error!^用户类型错误!
//    MSG_301251,//301251^Job is running,cannot be deleted!^Job正在运行，不能被删除!
//    MSG_301252,//301252^CreatedBy  not exist!^创建人不存在!
//    MSG_301253,//301253^bond_status is not right^绑定记录状态不对!
//    MSG_301254,//301254^Request was Locked Failed To Rejected!^提案处于上锁状态不能审批!
//    MSG_301255,//301255^Request must be in pending status^提案必须为Pending状态!
//    MSG_301256,//301256^newFlag should not same as old flag!^新状态不能与原状态一致!
//    MSG_301257,//301257^customer type not match!^用户类型不能为试玩用户！
//    MSG_301258,//301258^LoginName is already effective flag!^下线会员已经是有效标识！
//    MSG_301259,//301259^Customer has signed up!^会员已报名！
//    MSG_301260,//301260^Password status lock!^密码已锁定！
//    MSG_301261,//301261^New AP Key remain same as old key!^新德州扑克密码不能与原密码相同！
//    MSG_301262,//301262^New AP Key remain same as login password!^德州扑克密码与网站密码相同！
//    MSG_301263,//301263^PromotionSystemFlag should not same as current flag!^优惠开关状态不能与当前开关状态相同！
//    MSG_301264,//301264^Your USDT Account is invalidate,Please contact the customer service^您的USDT账户异常,请联系客服!
//    MSG_301265,//301265^The game account has been disabled, please contact customer service!^游戏帐户已被禁用，请联系客服!
//    MSG_301266,//301266^Multi Currency Account must be enabled!^主账号状态必须为启用！
//    MSG_301267,//301267^Parent's Name and Account's Name can't same^合作伙伴和真钱会员姓名不能一样!
//    MSG_301268,//301268^Parent loginName cannot same as yourself^上线登录名不能与自己登录名相同!
//    MSG_301270,//301270^tokenCode must generate by login!^TokenCode必须通过登录生成！
//    MSG_211106,//211106^MinDepositAmount1 not correct format,must be  **.000000!^普通VIP最小存款金额 格式不正确,格式必须为 **.000000!
//    MSG_307105,//307105^betweenBetAmount not correct format,must be **.000000!^betweenBetAmount格式不对，必须为**.000000
//    MSG_300099,//300107^LimitAmount format must be **.000000!^格式必须为**.000000！
//    MSG_211107,//211107^MinDepositAmount2 not correct format,must be  **.000000!^超级VIP最小存款金额 格式不正确,格式必须为 **.000000!
//    MSG_211108,//211108^MinBetAmount1 not correct format,must be  **.000000!^普通VIP最小有效投注额 格式不正确,格式必须为 **.000000!
//    MSG_211109,//211109^MinBetAmount2 not correct format,must be  **.000000!^超级VIP最小有效投注额 格式不正确,格式必须为 **.000000!
//    MSG_211114,//211114^DepositAmount not correct format,must be  **.000000!^当前周实际存款 格式不正确,格式必须为 **.000000!
//    MSG_211115,//211115^BetAmount not correct format,must be  **.000000!^当前周实际有效投注额 格式不正确,格式必须为 **.000000!
//    MSG_254701,//254701^Amount not correct format,must be  **.000000!^金额格式不正确,格式必须为 **.000000!
//    MSG_301702,//301702^RefAmount must be greater than zero,format must be **.000000!^参考额度必须大于0,格式必须为**.000000
//    MSG_301703,//301703^Amount must be greater than zero,format must be **.000000!^金额必须大于0,格式必须为**.000000
//    MSG_301704,//301704^BetAmount must be greater than zero,format must be **.000000!^取款所需投注额度必须大于0,格式必须为**.000000
//    MSG_334912,//334912^Amount format not correct format,must be **.000000!^借出/归还金额格式不对,必须为**.000000!
//    MSG_100098,//100020^Amount must be greater than zero,format must be **.000000!^Amount必须大于0,格式必须为**.000000!
//    MSG_300006,//300006^PreAmount must be greater than zero,format must be **.000000!^PreAmount必须大于0,格式必须为**.000000!
//    MSG_523147,//523147^oldAmount format not correct format,must be  **.000000!^旧金额格式必须为**.000000
//    MSG_512006,//512006^RaiseLevelAmount not correct format,must be  **.000000!^晋级礼金格式不正确,格式必须为 **.000000!
//    MSG_304852,//304852^Bet Amount format must be **.000000!^投注额格式必须为**.000000!
//    MSG_512007,//512007^Stock not correct format,must be  **.000000!^股票格式不正确,格式必须为 **.000000!
//    MSG_512008,//512008^AccountWorth not correct format,must be  **.000000!^账号价值格式不正确,格式必须为 **.000000!
//    MSG_512009,//512009^DepositAmount not correct format,must be  **.000000!^存款金额格式不正确,格式必须为 **.000000!
//    MSG_201702,//201702^Format of Credit must **.000000^试玩会员金额格式必须为**.000000
//    MSG_307002,//307002^BetAmount must be greater than zero,format must be **.000000!^有效投注额必须大于0,格式必须为**.000000!
//    MSG_301611,//301617^BtcAmount must be greater than zero,format must be **.00000000!^比特币额度必须大于0,格式必须为**.00000000!
//    MSG_301272,//301272^currency not match!^货币类型不一致！
//    MSG_301273,//301273^When batch commits,the user type must be consistent!^批量提交洗码时，用户类型必须一致！
//    MSG_301274,//301274^When batch commits,the XM mode must be consistent!^批量提交洗码时，洗码提交方式必须一致！
//    MSG_301275,//301275^When batch commits,the XMType must be consistent!^批量提交洗码时，洗码类型必须一致！
//    MSG_301276,//301276^Currency must be same as customer's currency!^货币类型需要与用户货币类型一致！
//    MSG_301277,//301277^customers must is real account!^会员必须是真钱会员!
//    MSG_301278,//301278^The agent currency type needs to be consistent with the member^代理商货币类型需要与会员的一致!
//    MSG_301279,//301279^product rate config should not be empty or more than 1 for customer type^每种客户类型的产品比例配置不能为空或者多余1条
//    MSG_301280,//301280^Only one sub account for each currency^同一主账号每个币种只能有一个子账号
//    MSG_301281,//301281^Multi Currency Account must a CNY account^主账号币种必须为产品默认币种
//    MSG_301282,//301282^check Id card exception.^身份证校验异常!
//    MSG_301283,//301283^Sub Account Name does not match with main account^子账号跟主账号不匹配
//    MSG_301284,//301284^tokenCode error:{0}^令牌编码错误:{0}
//    MSG_301285,//301285^wrong pwd,please enter again^密码错误，请重新输入
//    MSG_301286,//301286^The original password is incorrect, please enter again!^原密码输入有误,请重新输入！
//    MSG_301287,//301287^customers type must for try account!^会员类型只能为试玩会员！
//    MSG_301288,//301288^Password Error!^密码错误！
//    MSG_301289,//301289^RealName Don't match!^真实姓名不匹配！
//    MSG_301290,//301290^Phone Don't match!^绑定电话 不匹配！
//    MSG_301291,//301291^VerifyCode Don't match!^验证码 不匹配！
//    MSG_301292,//301292^AP bank account verify failed!^银行卡校验失败！
//    MSG_301293,//301293^old ApKey error!^旧德州扑克密码错误！
//    MSG_301294,//301294^Invalid password status!^密码状态错误！
//    MSG_301295,//301295^Customer status is disabled!^会员状态已禁用！
//    MSG_301296,//301296^Sub Account Name does not match with main account^子账号跟主账号不匹配
//    MSG_301297,//301297^token validate failed:{0}^token校验失败：{0}
//    MSG_301298,//301298^Update tokenCode error:{0}!^更新令牌编码码错误:{0}
//    MSG_301299,//301299^customerId and loginName not match!^客户ID和登录名不匹配
//    MSG_301301,//301301^Customer flag is incorrect^该客户信息状态不正确
//    MSG_301302,//301302^Currency mismatch!^币种不匹配
//    MSG_301303,//301303^Email/Phone is not bound!^会员邮箱/电话未绑定
//    MSG_301304,//301304^Phone is not bound!^会员电话未绑定
//    MSG_301305,//301305^Email is not bound!^会员邮箱未绑定
//    MSG_301306,//301306^Oldflag and requestFlag Don't match!^提案旧状态和数据库状态不匹配
//    MSG_301307,//301307^CustomerBankID if not of the customer!^会员信息必须属于该会员!
//    MSG_301308,//301308^Customer has been disabled!^会员已禁用!
//    MSG_301309,//301309^BankAccountName must and real name consistent!^银行开户名必须和真实姓名一致!
//    MSG_301310,//301310^Web add the bank information is only himself!^网站添加银行信息必须为本人!
//    MSG_301312,//301312^old flag must 0^旧的提案状态必须为0!
//    MSG_301313,//301313^This application has been reviewed!^该申请已经被审核!
//    MSG_301314,//301314^Recommend realName not exists!^推荐人真实姓名不存在!
//    MSG_301315,//301315^Recommend realNameBy not exists!^被推荐人真实姓名不存在!
//    MSG_301316,//301316^xmRate values are not equals!^洗码率值不相等!
//    MSG_301317,//301317^Currency must be same!^货币类型需要与转帐信息中的货币类型一致！
//    MSG_301318,//301318^Oldflag and requestFlag Don't match!^AMTC 转账旧状态和数据库状态不匹配！
//    MSG_301319,//301319^Signature does not match the old one^数据签名不匹配,审批失败!
//    MSG_301320,//301320^Account Exchange Request Failed To Approve!^额度兑换提案审批失败!
//    MSG_301321,//301321^Source Account must be real customer account!^转出账号必须为真钱账户!
//    MSG_301322,//301322^Target Account must be real customer account!^转入账号必须为真钱账户！
//    MSG_301323,//301323^Source and Target account does not match^转账账号不匹配!
//    MSG_301324,//301324^Withdrawal Request Failed To Approve!^取款提案审批失败!
//    MSG_301325,//301325^Currency is not consistent^输入的货币与客户的不一致!
//    MSG_301326,//301326^AMTC channel must only withdraw AMTC type!^来源自AMTC的用户只能取AMTC类型取款!
//    MSG_301327,//301327^Transfer to yeb account is disabled!^yeb转账状态为禁用！
//    MSG_301328,//301328^the record has already transfer out!^此条计息记录已经转出!
//    MSG_301329,//301329^the record's loginName must same as transfer's loginName!^计息记录登录名必须与转账登录名一致!
//    MSG_301330,//301330^the transfer records flag is not pendding!^转账记录状态必须为等待！
//    MSG_301331,//301331^The Records isn't transfer out, can not deleted!^未转出的转账记录不能删除！
//    MSG_301332,//301332^LoginName didn't match the bill login name!^登录名与单据登录名不一致！
//    MSG_301333,//301333^Function credit line is disable^信用额度记录功能未开启，不允许借出/归还操作！
//    MSG_301334,//301334^Loan/Return Currency not same as customers currency^借出/归还币种与客户币种不一致！
//    MSG_301335,//301335^Create Yeb Credit Line Log failed^创建信用额度借出/归还记录失败！
//    MSG_301336,//301336^Approve Yeb Credit Line Log failed^审批信用额度借出/归还记录失败！
//    MSG_301337,//301337^Update Yeb Credit Line Flag failed^更改过夜利息借出状态失败！
//    MSG_301338,//301338^Update Customer Credit Line failed^更改客户可用信用额度失败！
//    MSG_301339,//301339^Update Customer Game Creditfailed^更改客户游戏额度失败！
//    MSG_301340,//301340^Create init credit line log failed^创建初始化客户信用额度失败！
//    MSG_301341,//301341^Init Customer credit line failed^初始化客户信用额度失败！
//    MSG_301342,//301342^Clean init credit line log failed^创建清零客户信用额度失败！
//    MSG_301343,//301343^Clean Customer credit line failed^客户信用额度清零失败！
//    MSG_301344,//301344^CustomerType must normal customer!^会员类型必须是普通会员！
//    MSG_301345,//301345^Deposit Transaction Record's currency must be same as customer's currency!^存款记录的货币与用户的货币不同！
//    MSG_301346,//301346^LoginName Don't match!^登录名不匹配！
//    MSG_301347,//301347^Bond record is not phone record^不是手机绑定记录！
//    MSG_301348,//301348^request flag isn't correct!^提案状态不正确！
//    MSG_301349,//301349^parent currency must be the same with customer!^上线会员币种必须跟会员的币种一致！
//    MSG_301350,//301350^System-Account or password incorrect!^系统用户名或者密码不正确！
//    MSG_301351,//301351^customers must enable Integral!^会员积分必须启用！
//    MSG_301721,//301721^do not find the promotion config info.!^没有查到该优惠的配置信息
//    MSG_304925,//304934^xm config not find!^洗码配置不存在!
//    MSG_304838,//304838^Bank Account No does not existed!^取款账号不存在!
//    MSG_700400,//700401^List<WSTMPhoneDetail> can not by empty^List<WSTMPhoneDetail>不能为空
//    MSG_206600,//206606^code invalid!^验证码失效！
//    MSG_339000,//339000^WSTransfer should not be empty!^转账对象对象不能为空!
//    MSG_308575,//308575^Customer Operation List can not be null^客户操作列表不能为空
//    MSG_601007,//601007^Message Condition should not be empty!^消息条件不能为空!
//    MSG_605588,//605588^Remark Config does not existed^配置模板不存在
//    MSG_922015,//922015^referenceId not exist!^提案编号不存在!
//    MSG_828004,//828004^batch no and login name cannot be null!^批次号,登录名以及产品ID不能为空
//    MSG_400611,//400611^Role not exist!^角色不存在！
//    MSG_304900,//304900^rebate request have mast be not null!^洗码提案不能为空！
//    MSG_205302,//205302^Integral  not correct format,must be number!^积分格式不正确,格式必须为数字！
//    MSG_309807,//309807^CommunicateWithTime  must be a positive integer, the length is more than 3 !^沟通时长必须是一个正整数,长度不超过3
//    MSG_306504,//306504^Customer Level must be a positive integer, the length is more than 3!^客户等级必须是一个正整数,长度不超过3
//    MSG_300208,//300208^BetAmountMultiplier must be a positive integer, the length is more than 4!^BetAmountMultiplier必须是一个正整数,长度不超过4
//    MSG_300212,//300212^Max Allow must be a positive integer, the length is more than 5!^最大次数范围必须是一个正整数,长度不超过5
//    MSG_301402,//301402^PROMOTION_CONFIG_ID must be a positive integer, the length is more than 32!^配置优惠ID必须是一个正整数,长度不超过32.
//    MSG_202223,//202223^ApKey is already exist!^ApKey已经存在！
//    MSG_301514,//301514^real name has been exist,can no modify directly!^真实姓名己经存在，不能直接修改！
//    MSG_601010,//601010^Expired date must the format YYYY-MM-DD HH:MM:SS!^失效时间格式必须为YYYY-MM-DD HH:MM:SS！
//    MSG_311521,//311521^registryDateBegin not correct format,must be yyyy-mm-dd hh:mm:ss!^注册日期起格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311522,//311522^registryDateEnd not correct format,must be yyyy-mm-dd hh:mm:ss!^注册日期止格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311519,//311519^updateDateBegin not correct format,must be yyyy-mm-dd hh:mm:ss!^更新日期起格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311520,//311520^updateDateEnd not correct format,must be yyyy-mm-dd hh:mm:ss!^更新日期止格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311517,//311517^beginDate not correct format,must be yyyy-mm-dd hh:mm:ss!^创建日期起格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311518,//311518^endDate not correct format,must be yyyy-mm-dd hh:mm:ss!^创建日期止格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311507,//311507^CreateDateBegin not correct format,must be yyyy-mm-dd hh:mm:ss!^创建日期起格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_311508,//311508^CreateDateEnd not correct format,must be yyyy-mm-dd hh:mm:ss!^创建日期止格式必须为yyyy-mm-dd hh:mm:ss!
//    MSG_503101,//503103^firstBetTime not correct,must be yyyy-MM-dd HH:mm:ss^第一次下注时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_211103,//211103^MonthWeeks should not be empty,and length must be fixed 1 !^当前月份周数不能为空,长度必须为1!
//    MSG_211120,//211120^Weeks should not be empty,and length must be fixed 1 !^当前第几周不能为空,长度必须为1!
//    MSG_208003,//208003^smsCode should not be empty,and length must be fixed 32!^验证码不能为空,长度必须为32
//    MSG_201718,//201723^Pwd  should not be empty,and length must be fixed 32.^密码不能为空,长度必须为32.
//    MSG_301609,//301609^Keycode should not be empty,and length must be fixed 32!^密钥不能为空,长度必须为32!
//    MSG_400106,//400106^ProductId should not be empty,and length must be fixed 3!^产品ID不能为空,长度必须为3!
//    MSG_301002,//301002^RequestId should not be empty,and length must be fixed 1-19!^提案请求ID不能为空,长度必须为1-19!
//    MSG_202160,//202160^ApEffectiveDate should not be empty,and length must be fixed 19!^德州扑克优惠开始时间不能为空,长度必须为19！
//    MSG_301112,//301112^NewStatus should not be empty,and length must be fixed 1!^修改状态 有效/无效不能为空,长度必须为1！
//    MSG_400503,//400503^System-Account or password incorrect!^系统用户名或者密码不正确！
//    MSG_301780,//301780^Amount not correct format,must be number!^金额格式不正确,格式必须为整数!
//    MSG_400808,//400808^RoleId not correct format,must be number!^角色ID格式不正确,格式必须为number
//    MSG_301410,//301410^Customer Level must be a positive integer, the length is more than 3!^客户等级必须是一个正整数,长度不超过3
//    MSG_500128,//50128^conditionStart cannot be empty1^条件起始不能为空!
//    MSG_500129,//50129^conditionEnd cannot be empty1^条件结束不能为空!
//    MSG_300845,//300845^Bank Account No must number^银行卡号必须为数字
//    MSG_314351,//314351^Could not update credit. feeRate Must be number!^手续费率非数字！
//    MSG_201799,//201799^parent id not correct,must be number^上级ID格式不正确，必须为整数
//    MSG_509321,//509321^Amount must be greater than zero,format must be **.000000!^奖励金额必须大于0,格式必须为**.000000
//    MSG_305134,//305134^Format of Source Credit must **.000000^转出金额格式必须为**.000000(最多6位小数)!
//    MSG_305135,//305135^Format of Target Credit must **.000000^转入金额格式必须为**.000000(最多6位小数)!
//    MSG_304833,//304833^Format of BTC Amount must **.00000000^BTC金额最多8位小数[**.00000000]
//    MSG_304834,//304834^Rate of BTC to CNY must be number format's string and greater than 0 and should like **.00^BTC to CNY 兑换只能是大于0的数字,最多8位小数
//    MSG_337111,//337111^rate must be greater than zero,format must be decimal!^利率必须为正小数
//    MSG_302606,//302606^RefAmount not correct format,must be **.000000!^RefAmount格式不正确,格式必须为**.000000
//    MSG_302506,//302506^MaxAmount not correct format,must be **.000000!^最大额度格式不正确,格式必须为**.000000
//    MSG_300825,//300825^PriorityOrder not correct format,must be within two byte number!^银行优先标识格式不正确,格式必须为2位以内的数字
//    MSG_300904,//300904^ActType not right,must be int number!^额度更改类型不能为空,必须是两位以内正整数
//    MSG_314352,//314352^Could not update credit. feeRate Must be not less than 0!^手续费率小于0！
//    MSG_314353,//314353^Could not update credit. feeRate Must be not less than 0!^手续费率大于1！
//    MSG_334940,//334942^Customer init credit line can not less than after amount^还款后金额超出信用额度
//    MSG_305138,//305138^Source Customer available credit is less than transfer out credit^转出客户额度不足
//    MSG_305136,//305136^Source Credit must greater than zero^转出金额必须大于0
//    MSG_305141,//305141^Source Credit can not less than Minimum Transfer out Amount^转出金额必须大于最小转出金额
//    MSG_305142,//305142^Source Credit can not greater than Minimum Transfer out Amount^转出金额必须小于最大转出金额
//    MSG_304929,//304930^Valid Bet Amount must greater than 0^尚未参与洗码的有效投注额必须大于0
//    MSG_304928,//304931^Betting Amount is greater than {0}^尚未参与洗码的有效投注额不能小于本次投注额 {0}
//    MSG_304927,//304933^Input Rebate Amount must greater than Calculate Rebate Amount^输入的洗码金额不能大于计算出来的洗码金额
//    MSG_305137,//305137^Target Credit must greater than zero^转入金额必须大于0
//    MSG_334938,//334938^Customer game credit is insufficient^用户游戏额度余额不足
//    MSG_334915,//334915^Final Amount can not less than zero^最终金额不能小于0
//    MSG_334914,//334914^Loan out Credit Line can not greater than init credit amount^借出信用额度不能大于初始信用额度
//    MSG_334913,//334913^Loan out Credit Line can not greater than Customer credit line^借出信用额度不能大于客户可用信用额度
//    MSG_304819,//304819^Withdraw amount must not exceed local amount!^取款金额不能超过会员本地额度
//    MSG_308583,//308583^Withdrawal amount and conversion amount are not equal^取款金额和转换金额不相等
//    MSG_304882,//304882^Minimum Withdraw Amount greater than 0 !^最小取款金额必须大于0!
//    MSG_304883,//304882^Withdrawal Amount as least {0} {1}!^取款最低限额至少为{0} {1}!
//    MSG_304884,//304883^Withdrawal Amount as most {0} {1}!^取款最高限额为{0} {1}!
//    MSG_304885,//304884^Maxmum Withdraw Amount greater than 0 !^取款最高限额必须大于0!
//    MSG_304828,//304828^Withdrawals over the allowance {0}^取款已经超过当天限额：{0}
//    MSG_334937,//334938^Customer game credit is insufficient^用户游戏额度余额不足
//    MSG_503611,//503611^Format of request id not correct,must be number^ID格式不正确,必须为数字
//    MSG_506004,//506004^Format of id not correct,must be number^ID格式不正确,必须为数字
//    MSG_502245,//52245^distinceMonth cannot be empty^去重月份只能为数字格式
//    MSG_502246,//52245^longTimeNoBetDayStart not correct format,must be Integer Number^久未投注天数开始只能为数字格式
//    MSG_502247,//52245^longTimeNoBetDayEnd not correct format,must be Integer Number^久未投注天数结束只能为数字格式
//    MSG_507893,//57893^createStyle cannot be empty^创建方式不正确!
//    MSG_507892,//57892^totalDepositAmount must be number!^总存款金额格式不正确!
//    MSG_507891,//57891^rebateAmount must be number!^洗码流水格式不正确!
//    MSG_507890,//57890^validAmount must be number!^有效投注额格式不正确!
//    MSG_507889,//57889^promotionAmount must be number!^优惠格式不正确!
//    MSG_507888,//57888^profitAmount must be number!^盈利金额格式不正确!
//    MSG_507887,//57887^diffDepositWithdraw must be number!^存取款差格式不正确!
//    MSG_820014,//820014^Format of task id not correct,must be number^任务ID格式不正确,必须为数字
//    MSG_502241,//52245^longTimeNoBetDayStartl not correct format,must be Integer Number^久未投注天数开始只能为数字格式
//    MSG_502242,//52245^longTimeNoBetDayEndl not correct format,must be Integer Number^久未投注天数结束只能为数字格式
//    MSG_506156,//506156^Exclusive Line should be number^Exclusive Line应该为数字
//    MSG_826020,//826020^MinLevel not correct format,must be Integer Number!^最小星级格式不正确,格式必须为Integer Number
//    MSG_826021,//826021^MaxLevel not correct format,must be Integer Number!^最大星级格式不正确,格式必须为Integer Number
//    MSG_826022,//823021^Handle Type not correct format,must be Integer Number!^处理方式格式不正确,格式必须为Integer Number
//    MSG_823020,//823020^Task Detail Id not correct format,must be Integer Number!^任务详情ID格式不正确,格式必须为Integer Number
//    MSG_822023,//822023^Flag not correct format,must be Integer Number!^任务状态格式不正确,格式必须为Integer Number
//    MSG_400920,//400920^FunctionId not correct format,must be number!^功能菜单ID格式不正确,格式必须为number
//    MSG_506802,//506802^UrlConfg ID not correct format,must be number^线路配置ID格式不正确，必须为number
//    MSG_856008,//856008^Status not correct format,must be number^状态必须为整数
//    MSG_830011,//830011^arbitrageurType not correct format,must be number^套利类型格式不正确，必须为number
//    MSG_821012,//821012^Opreation Type not correct format,must be number^操作类型格式不正确，必须为number
//    MSG_506199,//506202^ExclusiveLine not correct format,must be number^专属专线值格式不正确，必须为number
//    MSG_334111,//334111^TransferAmount must a integer number!^转账金额必须为整数！
//    MSG_337101,//337101^MaxHoursnot correct format,must be number!^最大计息小时数格式不正确，必须为number
//    MSG_337114,//337114^PeriodHours should not be empty and should be int number!^周期间隔小时数不能为空, 必须为整数
//    MSG_334948,//334948^DepositMin should not be empty and should be int number!^存款要求下限不能为空, 必须为整数
//    MSG_203716,//203716^DepositLevel not correct format,must be number^存款优先级格式不正确，必须为number
//    MSG_201728,//201728^Partner can't have same phone number with member^合作伙伴与下线会员同电话
//    MSG_201715,//201715^Parent realName cannot same as yourself^上线真实姓名不能与自己真实姓名相同
//    MSG_201710,//201717^agent's ParentLoginName must is agent^代理会员的上线只能为代理
//    MSG_201716,//201716^real account's ParentLoginName must is partner or agent^真钱用户的上线只能为代理或合作伙伴
//    MSG_201705,//201722^RealName can't repeat!^很抱歉,该真实姓名已被注册,请联系客服,谢谢！
//    MSG_201706,//201722^Phone can't repeat!^很抱歉,该电话已被注册,请联系客服,谢谢！
//    MSG_201747,//201747^Id Card already existed^证件已存在
//    MSG_856010,//856010^One Customer only can bind one BTC account^一个客户只能绑定一个BTC账户
//    MSG_856011,//856011^One BTC account only can bind one Customer^一个账户只能被绑定一次
//    MSG_304968,//304968^Same login name same IP same terminal can only login once every {0} second!^相同登录名相同IP相同终端每{0}秒只能登录一次!
//    MSG_204302,//204302^the customer type should not be empty!^会员类型不能为空!
//    MSG_204301,//204301^the customer id should not be empty!^会员ID不能为空!
//    MSG_202144,//202145^RealName not exist!^真实姓名不存在！
//    MSG_202225,//202225^Reset count more than 5!^解锁大于5次！
//    MSG_203423,//203423^This Phone Had been activated on more than one account,please check it!^该手机号码已经在多与1个账号上激活，请检查该账号
//    MSG_206085,//206106^lastLoginDateBegin does not exist.^登录开始时间不存在
//    MSG_206086,//206107^lastLoginDateEnd does not exist.^登录结束时间不存在
//    MSG_206087,//206108^sourceCurrency does not exist.^源币种不存在
//    MSG_206088,//206109^targetCurrency does not exist.^目标币种不存在
//    MSG_505705,//505705^Unable to update task detail^不能更新任务详情
//    MSG_506701,//506801^Line Config ID should not be empty^专属专线配置ID不能为空
//    MSG_503401,//503401^LoginNameList or CustomerLevelList or DepositLevelList or PriorityLevelList or PhoneList should not be empty!^四个条件不能为空!
//    MSG_506401,//506401^{0} minutes send a number greater than {1}times!^{0}分钟内获取次数超过{1}次
//    MSG_305016,//305016^LoginName size must be ID size !^客户登录名和客户ID个数不匹配
//    MSG_300842,//300842^subAccount can not use [{0}] bank card!^子账号不能使用[{0}]币种银行卡！
//    MSG_300834,//300834^Catalog not right,must be {0} one of!^账户类别不正确的值,值只能为 {0} 其中之一
//    MSG_307011,//307011^WSCustomers's have unfinished same type modify level!^会员有未处理的修改等级提案!
//    MSG_307040,//307040^WSCustomers's have unfinished Modify Phone Request!^会员有未处理的修改电话提案!
//    MSG_307022,//307022^Can not activated/inactivated unbind phone^旧电话未绑定，无法操作
//    MSG_307038,//307038^Please set password first^请先设置密码
//    MSG_307039,//307039^Unbind old phone failed^解除旧手机状态失败
//    MSG_303612,//303612^Approve By does not existed^审批人不存在
//    MSG_303615,//303615^Real Name Request Failed To Approve^审批提案失败
//    MSG_301779,//301779^Raise level flag not open!^晋级状态未开启
//    MSG_311104,//311104^It was received this month!^这个月已经领取过该优惠！
//    MSG_301778,//301778^Deposit amount cannot blow Deposit limit Amount!^存款金额不能小于月存款限额！
//    MSG_301772,//301772^Apply refAmount cannot blow config minDepositAmount!^申请的参考金额不能低于优惠配置存款区间最小存款金额！
//    MSG_301722,//301722^promotion apply times more than require.^优惠申请次数大于最大申请次数
//    MSG_306156,//306156^Can not get more than one special XM config^获取的特殊客户洗码配置超过一个
//    MSG_304915,//304929^Failed to Update Customer Game Credit^修改用户额度失败
//    MSG_305148,//305148^Target Credit is not right^转入金额不正确
//    MSG_307815,//307815^Request does not existed!^提案不存在!
//    MSG_203109,//203109^Amount is wrong!^金额错误!
//    MSG_300860,//300860^protocol not exist!^协议不存在！
//    MSG_203110,//203110^Withdraw deduct promotion amount failed!^取款取消或拒绝后扣除优惠下发额度失败!
//    MSG_305149,//305151^This customer's deposit level is -19, can not create this withdraw!^信用级为黑名单 -19，不支持 CNY 银行卡取款!
//    MSG_201700,//201713^branchName already exist!^门店名称已存在!
//    MSG_334930,//334930^Same Type Credit Line Logs already existed^存在同类型的待审提案
//    MSG_334919,//334919^Function credit line is disable^信用额度记录功能未开启，不允许批准借出/归还操作
//    MSG_201334,//201334^relationValue have isExit^关联值已经存在
//    MSG_203111,//203111^update credit log failed!^更新额度校验结果失败
//    MSG_304340,//304344^Account Must Not Have less than zero credit result!^用户额度更改后不能小于0
//    MSG_314346,//314346^Could not update credit. Real Account Must Not Have less than zero credit result!^额度不够！
//    MSG_300001,//300001^Exceeded the maximum(5) waiting payment^等待付款-笔数超过五笔
//    MSG_203501,//203503^CustomerBankId/bankCity/bankCountry can not be empty at same time^会员银行ID/开户银行不能同时为空
//    MSG_202162,//202162^Can not pass more than 1000 login name one time^批量查询一次不能超过1000个登录名
//    MSG_304890,//304890^Currency not same as customers currency^币种与客户币种不一致
//    MSG_202163,//202162^Can not pass more than 100 login name one time^批量查询一次不能超过100个登录名
//    MSG_391011,//391011^Master Id can not be null^Master Id不能为空
//    MSG_305858,//305858^4-12 characters, only alphabets or with numbers!^登录名不能为空,长度必须在4至12之间!
//    MSG_202164,//202164^Can not pass more than 100 customer id one time^批量查询一次不能超过100个客户ID
//    MSG_203101,//203101^LoginName length must for 1 to 1000 between!^登录名 长度必须在1和1000之间!
//    MSG_202841,//202841^Unable to Update Try Customer Credit, Update Row is:{0}^无法更新额度,更新行数:{0}
//    MSG_205003,//205003^CustomerId not correct format,must be number^会员ID格式不正确，必须为number
//    MSG_826028,//826028^Id  not correct format,must be number!^ID格式不正确,格式必须为number!
//    MSG_831005,//831005^Customer Level not correct format,must be number^客户星级格式不正确，必须为number
//    MSG_831006,//831006^Trust Level not correct format,must be number^信用级格式不正确，必须为number
//    MSG_831007,//831007^Priority Level not correct format,must be number^优先级格式不正确，必须为number
//    MSG_831009,//831009^Risk Level not correct format,must be number^风险等级格式不正确，必须为number
//    MSG_400809,//400809^Flag not correct format,must be number!^Flag标识格式不正确,格式必须为number
//    MSG_205004,//205004^TypeIntegral not correct format,must be number!^积分类型格式不正确,格式必须为数字！
//    MSG_200207,//200207^AgentCommissionId is not correct format,must be number!^佣金记录ID格式不正确,格式必须为整数！
//    MSG_200706,//200706^CreditLogId not correct format,must be number^额度记录ID格式不正确，必须为number
//    MSG_856004,//856004^Status must be number^Status 必须为数字
//    MSG_856005,//856005^Type must be number^Type 必须为数字
//    MSG_211104,//211104^MonthWeeks  not correct format,must be number!^当前月份周数格式不正确,格式必须为number!
//    MSG_211121,//211121^Weeks  not correct format,must be number!^当前第几周格式不正确,格式必须为number!
//    MSG_308405,//308405^PriorityType not correct format,must be number!^优先类型格式不正确,格式必须为number
//    MSG_200803,//200803^Agent Id not correct format,must be number!^代理 ID格式不正确,格式必须为number
//    MSG_201303,//201303^RelationType must be number!^RelationType必须是数字
//    MSG_201300,//201306^relationFlag must be number!^relationFlag必须是数字
//    MSG_201301,//201309^relationId must be number!^relationId必须是数字
//    MSG_202607,//202607^Customer Bank Id not correct format,must be number^会员银行ID格式不正确，必须为number
//    MSG_200709,//200709^Format of EndPoint must be Number!^提交来源必须为数字类型!
//    MSG_337013,//337013^periodInterestLimit should be int number!^当期利息限额必须为整数
//    MSG_337014,//337014^recentDaysLimit should be int number!^近X天天数限制必须为整数
//    MSG_337015,//337015^recentAmountLimit should be int number!^近X天利息限额必须为整数
//    MSG_334949,//334949^DepositMax should be int number!^存款要求上限不能为空, 必须为整数
//    MSG_334950,//334950^RateMin should be int number!^年利率下限不能为空, 必须为整数
//    MSG_334951,//334951^RateMax should be int number!^年利率上限不能为空, 必须为整数
//    MSG_201730,//201732^Format of Phone must Integer Number^会员电话必须为整数
//    MSG_201729,//201733^PriorityOrder not correct format,must be number !^银行优先标识格式不正确,格式必须为number
//    MSG_826005,//826005^MinLevel not correct format,must be Integer Number!^最小星级格式不正确,格式必须为Integer Number
//    MSG_826006,//826006^MaxLevel not correct format,must be Integer Number!^最大星级格式不正确,格式必须为Integer Number
//    MSG_203718,//203718^Integral not correct format,must be number^会员积分格式不正确，必须为number
//    MSG_203719,//203719^IsMarket not correct format,must be number^市场部标识格式不正确，必须为number
//    MSG_203720,//203720^Question1 not correct format,must be number^问题1格式不正确，必须为number
//    MSG_203721,//203721^Question2 not correct format,must be number^问题2格式不正确，必须为number
//    MSG_203723,//203723^promotionFlag not correct format,must be number^优惠状态格式不正确，必须为number
//    MSG_203724,//203724^xmFlag not correct format,must be number^洗码状态格式不正确，必须为number
//    MSG_203205,//203205^CustomerContributionId not correct format,must be number!^用户贡献ID格式不正确,格式必须为number
//    MSG_506122,//506122^ExclusiveLine must Integer value^专属专线值必须为整数
//    MSG_203711,//203711^FingerId not correct format,must be number^记录编号格式不正确，必须为number
//    MSG_303305,//303305^NewStatus not correct format,must be number!^类型新状态格式不正确,格式必须为number！
//    MSG_303306,//303306^OldStatus not correct format,must be number!^类型旧状态格式不正确,格式必须为number！
//    MSG_820012,//820012^Job Status not correct format,must be number^Job状态格式不正确，必须为number
//    MSG_820021,//820021^Job Repeat Count not correct format,must be number^Job重复次数格式不正确，必须为number
//    MSG_820022,//820022^Job Run Times not correct format,must be number^Job运行次数格式不正确，必须为number
//    MSG_820025,//820025^Job Running Status not correct format,must be number^Job运行状态格式不正确，必须为number
//    MSG_820026,//820026^Repeat Interval not correct format,must be number^重复间隔格式不正确，必须为number
//    MSG_820018,//820030^Task Id not correct format,must be number^任务ID格式不正确，必须为number
//    MSG_302505,//302505^PromotionConfigID not correct format,must be number!^优惠配置ID格式不正确,格式必须为number
//    MSG_302507,//302507^MaxAllow not correct format,must be number!^MaxAllow格式不正确,格式必须为number
//    MSG_302508,//302508^MinDeposit not correct format,must be number!^MinDeposit格式不正确,格式必须为number
//    MSG_302510,//302510^BetAmountMultiplier not correct format,must be number!^BetAmountMultiplier格式不正确,格式必须为number
//    MSG_306903,//306903^CustomerType not correct format,must be number!^会员类型格式不正确,格式必须为number
//    MSG_306905,//306905^MaxBetAmount not correct format,must be number!^最大投注额格式不正确,格式必须为number
//    MSG_306906,//306906^MinBetAmount not correct format,must be number!^最小投注额格式不正确,格式必须为number
//    MSG_306907,//306907^OldId not correct format,must be number!^旧ID格式不正确,格式必须为number
//    MSG_205804,//205804^IntegralBalance not correct format,must be number!^积分类型格式不正确,格式必须为数字！
//    MSG_403205,//403205^Id not correct format,must be number^产品游戏关联ID格式不正确，必须为number
//    MSG_503909,//503909^Announcement Id not correct format,must be number^公告ID格式不正确，必须为number
//    MSG_503910,//503910^Type not correct format,must be number^公告网站类型格式不正确，必须为number
//    MSG_505511,//505511^Partner not correct format,must be number^是否适用于合作伙伴格式不正确，必须为number
//    MSG_505512,//505512^ShowType not correct format,must be number^显示类型格式不正确，必须为number
//    MSG_309904,//309904^CallFlag  not correct format,must be number!^电话状态格式不正确,格式必须为数字
//    MSG_309905,//309905^CommunicateWithTime  not correct format,must be number!^通话时间格式不正确,格式必须为数字
//    MSG_402705,//402705^ConfigurationId not correct format,must be number^产品游戏洗码配置ID格式不正确，必须为number
//    MSG_304105,//304109^SameFlag not correct format,must be number!^优先类型格式不正确,格式必须为number
//    MSG_822009,//822009^JwGiftTimes not correct format,must be Integer Number!^礼物发放次数格式不正确,格式必须为Integer Number
//    MSG_822020,//822023^TaskId not correct format,must be Integer Number!^任务ID格式不正确,格式必须为Integer Number
//    MSG_822042,//822042^OpreationTimes not correct format,must be Integer Number!^处理次数格式不正确,格式必须为Integer Number
//    MSG_822048,//822048^IsEnable not correct format,must be Integer Number!^是否可用格式不正确,格式必须为Integer Number
//    MSG_302705,//302705^Amount not correct format,must be number!^金额格式不正确,格式必须为number
//    MSG_302706,//303306^LevelStatus not correct format,must be number!^会员等级格式不正确,格式必须为number
//    MSG_302707,//303307^OldLevelStatus not correct format,must be number!^会员旧等级格式不正确,格式必须为number
//    MSG_827043,//827043^DepositMin not correct format,must be Integer Number!^最小存款格式不正确,格式必须为Integer Number
//    MSG_827044,//827044^DepositMax not correct format,must be Integer Number!^最大存款格式不正确,格式必须为Integer Number
//    MSG_827045,//827045^BetAmountMin not correct format,must be Integer Number!^最小投注格式不正确,格式必须为Integer Number
//    MSG_827046,//827046^BetAmountMax not correct format,must be Integer Number!^最大投注格式不正确,格式必须为Integer Number
//    MSG_400709,//400709^RoleId not correct format,must be number!^角色ID格式不正确,格式必须为number
//    MSG_400711,//400711^TimeStatus not correct format,must be number!^时间控制格式不正确,格式必须为number
//    MSG_825003,//825003^VipTypeId not correct format,must be Integer Number!^VIP类型格式不正确,格式必须为Integer Number
//    MSG_825004,//825004^LineId not correct format,must be Integer Number!^服务经理ID格式不正确,格式必须为Integer Number
//    MSG_825020,//825020^Income not correct format,must be Number!^收入格式不正确,格式必须为 Number
//    MSG_400601,//400601^FunctionId not correct format,must be number!^功能菜单ID格式不正确,格式必须为number
//    MSG_400603,//400603^FunctionType not correct format,must be number!^菜单类型格式不正确,格式必须为number
//    MSG_202245,//202245^Unable to update customer {0} effective flag!^无法更新客户 {0} 的有效标志位!
//    MSG_202246,//202246^Unable to update parent {0} effective flag!^无法更新下线 {0} 的有效标志位!
//    MSG_523104,//523104^masterId shouldn't empty!^Master Id不能为空！
//    MSG_306148,//306148^Same product, same customer level, same currency cannot be repeated!^相同产品,相同客户星级,相同币种不能重复!
//    MSG_306147,//306147^Same user name, same product, same xm type cannot be repeated!^相同用户名,相同产品,相同洗码类型不能重复!
//    MSG_204300,//204302^the customer type should not be empty^会员类型不能为空
//    MSG_205903,//205903^id too much!^id传入过多!
//    MSG_523129,//523129^Sync Last Bet Info failed^同步客户最后投注信息失败
//    MSG_506225,//506225^LineId cannot be empty!^LineId不能为空
//    MSG_506224,//506224^records not exist!^记录不存在
//    MSG_506800,//506801^lineConfg  should not be empty!^线路配置ID不能为空!
//    MSG_601105,//601105^The message flag must 0 when it audit!^只能审批等待状态的消息！
//    MSG_601402,//601402^message Ids length must less 1000^消息ID的个数必须少于1000
//    MSG_601104,//601105^The message flag must 0 when it audit!^只能审批等待状态的消息！
//    MSG_300306,//300306^ConfigId Does not existed^优惠配置ID不存在
//    MSG_300305,//300306^Remark Template exist already can't repeat!^模版己经存在不能重复!
//    MSG_206617,//206618^submit a number greater than {0} times!^提交错误次数超过{0}次
//    MSG_301510,//301510^more than 20 requests today!^当天超过20条提案!
//    MSG_610209,//610209^SumDateBegin or SumDateEnd should not be empty !^统计开始时间或结束时间不能为空!
//    MSG_300854,//300854^Bank Id does not existed^Bank Id不存在
//    MSG_300835,//300837^Confirm bank request failed^确认银行卡失败
//    MSG_380109,//380109^Can not activated unbind phone^该电话未绑定，无法激活
//    MSG_307817,//307817^Request already has been in Locked/unLocked status^提案已经处于锁定/解锁状态
//    MSG_307818,//307818^Unable to Lock Credit Modification Request!^锁定/解锁提案失败!
//    MSG_310114,//310114^email request is the same with the current email info!^新邮箱和当前的邮箱相同!
//    MSG_701030,//701030^the new parent's parent can't be himself!^新上级的上级递归中不能包含当前用户自己！
//    MSG_503102,//503103^Flag  not right,must be one of [0,1]!^标识不正确的值,值只能为[0,1]其中之一
//    MSG_202320,//202320^userType not right,must be[U,C,S] one of!^不正确的值,值只能为[U,C,S] 其中之一!
//    MSG_100022,//100022^Type must in [0,1]^Type 值必须在[0,1]之间
//    MSG_201003,//201003^VipFlag not right,must be 0,1!^VIP标识不正确的值,值只能为0,1其中之一!
//    MSG_201004,//201004^IsStandards not right,must be 0,1,-1!^当前月是否达标不正确的值,值只能为0,1,-1其中之一!
//    MSG_206017,//206017^ContributionType should not be empty , and must be one of [0,1,2]!^贡献类型不能为空且值必须为[0,1,2]其中之一
//    MSG_201100,//201101^flag not right,must be [2,-2]!^flag 不正确的值,值只能为[2,-2]其中之一
//    MSG_201006,//201006^UpdateDepositFlag not right,must be 0,1!^存款修改状态不正确的值,值只能为0,1其中之一!
//    MSG_211105,//211105^IsStandards not right,must be 0,1,-1!^当前月是否达标不正确的值,值只能为0,1,-1其中之一!
//    MSG_211099,//211105^UpdateDepositFlag not right,must be 0,1!^存款修改状态不正确的值,值只能为0,1其中之一!
//    MSG_211117,//211117^UpdateBetFlag not right,must be 0,1!^有效投注额修改状态不正确的值,值只能为0,1其中之一!
//    MSG_211124,//211124^weeks not right,must be [1,2,3,4,5]!^weeks不正确的值,值只能为[1,2,3,4,5]其中之一!
//    MSG_254706,//254706^transCode must in 111517,111518 111520 one of^转账代号必须在111517,111518,111520之中
//    MSG_680002,//680002^MatchType not right,must be 1-11 one of!^匹配类型不正确,必须为1-11
//    MSG_202404,//202404^Deposit  not right,must be one of [0,1]!^存款的值只能为[0,1]其中之一
//    MSG_202405,//202405^Withdrawal  not right,must be one of [0,1]!^取款 的值只能为[0,1]其中之一
//    MSG_202406,//202406^Promotions  not right,must be one of [0,1]!^优惠 的值只能为[0,1]其中之一
//    MSG_202407,//202407^ModifyPassword  not right,must be one of [0,1]!^修改密码 的值只能为[0,1]其中之一
//    MSG_202408,//202408^ModifyPhone  not right,must be one of [0,1]!^修改电话 的值只能为[0,1]其中之一
//    MSG_202409,//202409^ModifyBankingData  not right,must be one of [0,1]!^修改银行资料 的值只能为[0,1]其中之一
//    MSG_202410,//202410^ModifyAccountName  not right,must be one of [0,1]!^修改帐户姓名 的值只能为[0,1]其中之一
//    MSG_202411,//202411^NotifyPromotions  not right,must be one of [0,1]!^优惠通知 的值只能为[0,1]其中之一
//    MSG_202412,//202412^NewPaymentAccount  not right,must be one of [0,1]!^收款帐号变更 的值只能为[0,1]其中之一
//    MSG_202413,//202413^NewWebsite  not right,must be one of [0,1]!^域名变更通知 的值只能为[0,1]其中之一
//    MSG_202414,//202414^Regards  not right,must be one of [0,1]!^网站问候 的值只能为[0,1]其中之一
//    MSG_202415,//202415^SpecificMsg  not right,must be one of [0,1]!^特殊短信 的值只能为[0,1]其中之一
//    MSG_202422,//202422^Login  not right,must be one of [0,1]!^登录短信 的值只能为[0,1]其中之一
//    MSG_202423,//202423^NobleMetal  not right,must be one of [0,1]!^贵金属评论 的值只能为[0,1]其中之一
//    MSG_202424,//202424^Forex  not right,must be one of [0,1]!^外汇评论 的值只能为[0,1]其中之一
//    MSG_202420,//202420^UserType  not right,must be one of [U,C]!^用户类型值只能为[U,C]其中之一
//    MSG_804010,//804008^TokenType shouldn't be empty, must in {0} one of!^Token类型不能为空必须在{0}之中！
//    MSG_201780,//201780^Customer Type must 0 or 4^客户类型必须为0或者4
//    MSG_201777,//201777^EndpointType must in {0}!^终端类型必须在{0}之中！
//    MSG_301300,//301313^phonePrefix must in {0}.^phonePrefix 必须在{0}之中.
//    MSG_201748,//201748^Question1 not right,must in 1 to 10.^问题1不正确的值,值只能为1到10之间.
//    MSG_201750,//201750^Question2 not right,must in 1 to 10.^问题2不正确的值,值只能为1到10之间.
//    MSG_209003,//209003^MatchType not right,must in 1 to 10!^接口敏感信息匹配类型只能为1到10其中之一!
//    MSG_209004,//209003^Customer Type not right,must in one of {0}!^会员类型必须是{0}其中之一!
//    MSG_205303,//205303^IntegralType not right,must be 0,1,2,3,4,5,6,7,8,98,99 one of!^积分类型不正确的值,值只能为 0,1,2,3,4,5,6,7,8,98,99 其中之一!
//    MSG_205304,//205304^IntegralAction not right,must be ADD,SUB,RET,CLE one of!^操作类型不正确的值,值只能为 ADD,SUB,RET,CLE 其中之一!
//    MSG_207002,//207002^CheckType not right,must be +StringUtils.join(checkTypeArray)+ one of!^检查类型不正确的值,值只能为 +StringUtils.join(checkTypeArray)+ 其中之一
//    MSG_505701,//505701^Value of Flag must in [1,-1]^Flag值必须为1或者-1
//    MSG_210001,//210001^MatchType not right,must be {0} one of!^获取敏感信息类型不正确的值,值只能为{0}其中之一
//    MSG_305905,//305905^Downline disxm must be [0,1]^Downline disxm的值必须为[0,1]
//    MSG_305904,//305904^Downline lineArbit must be [0,1]^Downline Arbit的值必须为[0,1]
//    MSG_506708,//506708^infFlag  not right,must be one of [1,2,3]!^调用接口的标识值只能为[1,2,3]其中之一
//    MSG_202158,//202158^IsMarket must in 0,1!^市场部标识必须在 0,1 之中！
//    MSG_202132,//202132^Mt5Flag must in 0,1,2,3,4,5,6,7,8!^Mt5Flag标识必须在 0,1,2,3,4,5,6,7,8 之中！
//    MSG_202237,//202237^Default account flag not right,must be zero or one!^默认账号标识不正确的值,值只能0或者1其中之一
//    MSG_202159,//202164^IsSpecial must in 0,1!^Special标识必须在 0,1 之中！
//    MSG_202168,//202168^PhoneFlag must in 0,1!^电话标识必须在 0,1 之中！
//    MSG_202171,//202174^IntegralFlag must in 0,1!^积分状态必须在 0,1 之中！
//    MSG_202175,//202175^PromotionFlag must in 0,1!^优惠状态必须在 0,1 之中！
//    MSG_202176,//202176^XmFlag must in 0,1!^洗码状态必须在 0,1 之中！
//    MSG_202178,//202178^HGFlag must in 0,1!^论坛标识必须在0,1之中！
//    MSG_202177,//202178^TryWorthFlag must in 0,1,2!^论坛标识必须在0,1,2之中！
//    MSG_202154,//202154^Question1 must in 1,2,3,4,5!^问题1必须在1,2,3,4,5之中！
//    MSG_202156,//202156^Question2 must in 1,2,3,4,5!^问题2必须在1,2,3,4,5之中！
//    MSG_202179,//202178^CallFlag must in 0,1!^提案标识必须在0,1之中！
//    MSG_202169,//202179^showBalance must in 1,2!^显示余额必须在1,2之中！
//    MSG_304902,//304902^PromotionSystemFlag should not null, must in 0,1!^优惠开关状态不能为空，必须在 0,1 之中！
//    MSG_501500,//501508^beginwinDate not correct format,must be yyyy-MM-dd^时间始格式不正确,格式必须为yyyy-MM-dd
//    MSG_501501,//501509^endwinDate not correct format,must be yyyy-MM-dd^时间始格式不正确,格式必须为yyyy-MM-dd
//    MSG_503201,//503209^BettingDate not correct format,must be yyyy-MM-dd^下注时间格式不正确,格式必须为yyyy-MM-dd
//    MSG_503210,//503210^taskStartTime is not valid!^任务开始时间日期格式不正确!
//    MSG_503211,//503211^taskEndTime is not valid!^任务结束时间日期格式不正确!
//    MSG_503199,//503212^conditionStartTime is not valid!^条件开始时间日期格式不正确!
//    MSG_503213,//503213^conditionEndTime is not valid!^条件结束时间日期格式不正确!
//    MSG_503214,//503214^calculateDate not correct format,must be yyyy-MM-dd^需要计算连赢的日期格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503215,//503215^startDate not correct format,must be yyyy-MM-dd^时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503216,//503216^endDate not correct format,must be yyyy-MM-dd^时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503217,//503217^StartBettingDate not correct format,must be yyyy-MM-dd^下注开始时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503218,//503218^Endbettingdate not correct format,must be yyyy-MM-dd^下注结束时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503219,//503219^Format of statisticsDateBegin is not correct !^statisticsDateBegin格式不正确,必须为yyyy-MM-dd!
//    MSG_503220,//503220^Format of statisticsDateEnd is not correct !^statisticsDateEnd格式不正确,必须为yyyy-MM-dd!
//    MSG_503221,//503221^BeginDate not correct format,must be yyyy-MM-dd^汇总时间起格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503222,//503222^EndDate not correct format,must be yyyy-MM-dd^汇总时间止格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503223,//503223^sumDateBegin not correct format,must be yyyy-MM-dd^sumDateBegin格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503224,//503224^sumDateEnd not correct format,must be yyyy-MM-dd^sumDateEnd格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503225,//503225^SR Date not correct format,must be yyyy-MM-dd^生日格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503226,//503226^Data Begin not correct format,must be yyyy-MM-dd^开始时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503227,//503227^Data End not correct format,must be yyyy-MM-dd^结束时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503228,//503228^BirthDay not correct format,must be yyyy-MM-dd^生日格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503229,//503229^ReservationtimeBegin not correct format,must be yyyy-MM-dd^预订时间起格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503230,//503230^ReservationtimeEnd not correct format,must be yyyy-MM-dd^预订时间止格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503231,//503231^CreatedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^创建时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503232,//503232^CreatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^创建时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503233,//503233^LastUpdatedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss^审核时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503234,//503234^LastUpdatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss^审核时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503235,//503235^Reservationtime not correct format,must be yyyy-MM-dd^预订时间格式不正确,格式必须为yyyy-MM-dd!
//    MSG_503236,//503236^BirthDate not correct format,must be yyyy-MM-dd HH:mm:ss^生日格式不正确，应该为yyyy-MM-dd HH:mm:ss!
//    MSG_503237,//503237^StartDate  not correct format,must be yyyy-MM-dd HH:mm:ss!^活动开始时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503238,//503238^EndDate  not correct format,must be yyyy-MM-dd HH:mm:ss!^活动开始时间格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503239,//503239^CustomerCreatedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^客户创建时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503240,//503240^CustomerCreatedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^客户创建时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503241,//503241^CustomerLastLoginDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^客户更新时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503242,//503242^CustomerLastLoginDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^客户更新时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503244,//503244^Format of Job Next Fire Time not correct,must be yyyy-MM-dd HH:mm:ss^Job下次触发时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503245,//503245^Format of Job Prev Fire Time not correct,must be yyyy-MM-dd HH:mm:ss^Job下次触发时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503246,//503246^Format of Job Begin Time not correct,must be yyyy-MM-dd HH:mm:ss^Job开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503247,//503247^Format of Job End Time not correct,must be yyyy-MM-dd HH:mm:ss^Job结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503248,//503248^Format of Created Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^创建时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503249,//503249^Format of Create Date End not correct,must be yyyy-MM-dd HH:mm:ss^创建结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503250,//503250^Format of End Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^结束开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503251,//503251^Format of End Date End not correct,must be yyyy-MM-dd HH:mm:ss^结束结束时间时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503252,//503252^Format of Last Update Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^最后更新开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503253,//503253^Format of Last Update Date End not correct,must be yyyy-MM-dd HH:mm:ss^最后更新结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503254,//503254^Format of Registr Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^注册开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503255,//503255^Format of Registr Date End not correct,must be yyyy-MM-dd HH:mm:ss^注册结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503256,//503256^Format of Update Date Begin not correct,must be yyyy-MM-dd HH:mm:ss^更新开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503257,//503257^Format of Update Date End not correct,must be yyyy-MM-dd HH:mm:ss^更新结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503258,//503258^LastUpdateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^最后修改时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503259,//503259^LastUpdateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^最后修改时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503260,//503260^AuditDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^审核时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503261,//503261^AuditDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^审核时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503262,//503262^LastDepositDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^最后存款时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503263,//503263^LastDepositDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^最后存款时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss!
//    MSG_503264,//503264^Format of Effect date not correct,must be yyyy-MM-dd HH:mm:ss^生效时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503265,//503265^DepositDateBegin  not correct format,must be yyyy-MM-dd HH:mm:ss!^存款创建时间范围开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503266,//503266^DepositDateEnd  not correct format,must be yyyy-MM-dd HH:mm:ss!^存款创建时间范围结束时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503267,//503267^RequestCreateDateBegin  not correct format,must be yyyy-MM-dd HH:mm:ss!^提款提案创建范围开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503268,//503268^RequestCreateDateEnd  not correct format,must be yyyy-MM-dd HH:mm:ss!^提款提案创建范围开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_503269,//503269^StartDateBegin not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码开始时间起格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_503270,//503270^StartDateEnd not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码开始时间止 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_503271,//503271^EndDateBegin not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间起 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_503272,//503272^EndDateEnd not correct format,must be 'yyyy-MM-dd HH:mm:ss'^ 洗码结束时间止 格式不正确，格式必须为'yyyy-MM-dd HH:mm:ss'
//    MSG_503273,//503273^EffectiveDateBegin  not correct format,must be yyyy-MM-dd HH:mm:ss^有效时间起格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_503274,//503274^EffectiveDateEnd  not correct format,must be yyyy-MM-dd HH:mm:ss^有效时间止格式不正确,格式必须为yyyy-MM-dd HH:mm:ss
//    MSG_503275,//503275^Format of FirstAddRealNameDate not correct,must be yyyy-MM-dd HH:mm:ss^首次完善真实姓名时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503276,//503276^Format of UpdatedDateStart not correct,must be yyyy-MM-dd HH:mm:ss^更新时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503277,//503277^Format of UpdatedDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^更新时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503278,//503278^Format of EffectivityDateBegin not correct,must be yyyy-MM-dd HH:mm:ss^有效时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503279,//503279^Format of EffectivityDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^有效时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503280,//503280^Format of ExpiryDateBegin not correct,must be yyyy-MM-dd HH:mm:ss^逾期时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503281,//503281^Format of ExpiryDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^逾期时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503282,//503282^Format of LastLoginDateBegin not correct,must be yyyy-MM-dd HH:mm:ss^逾期时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503283,//503283^Format of LastLoginDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^逾期时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503284,//503284^Format of Begin Time not correct,must be yyyy-MM-dd HH:mm:ss^开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503285,//503285^Format of End Time not correct,must be yyyy-MM-dd HH:mm:ss^结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503286,//503286^Format of LoginTimeBegin not correct,must be yyyy-MM-dd HH:mm:ss^登录时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503287,//503287^Format of LoginTimeEnd not correct,must be yyyy-MM-dd HH:mm:ss^登录时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503288,//503288^Format of BeginCreatedTime not correct,must be yyyy-MM-dd HH:mm:ss^创建时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503289,//503289^Format of EndCreatedTime not correct,must be yyyy-MM-dd HH:mm:ss^创建时间起格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503290,//503290^Format of ApproveDateBegin not correct,must be yyyy-MM-dd HH:mm:ss^审批开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503291,//503291^Format of ApproveDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^审批结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503292,//503292^Format of BeginDate not correct,must be yyyy-MM-dd HH:mm:ss^开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503293,//503293^Format of EndDate not correct,must be yyyy-MM-dd HH:mm:ss^开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503294,//503294^DateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503295,//503295^DateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^开始时间格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503296,//503296^Format of backupDate not correct,must be yyyy-MM-dd HH:mm:ss^备份时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503297,//503297^Format of RegistryDateBegin not correct,must be yyyy-MM-dd HH:mm:ss^注册开始时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503298,//503298^Format of Format of RegistrDateEnd not correct,must be yyyy-MM-dd HH:mm:ss^注册结束时间格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503299,//503299^ProcessedDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^取款处理时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503300,//503300^ProcessedDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^取款处理时间止格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503301,//503301^Format of lockedDate not correct,must be yyyy-MM-dd HH:mm:ss^锁定日期格式不正确,必须为 yyyy-MM-dd HH:mm:ss
//    MSG_503302,//503302^HandleDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^处理开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503303,//503303^HandleDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^处理开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503304,//503304^JwLastTime not correct format,must be yyyy-MM-dd HH:mm:ss!^久未最后一次时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503305,//503305^ProApplyLastTime not correct format,must be yyyy-MM-dd HH:mm:ss!^优惠上次申请时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503306,//503306^YyTime not correct format,must be yyyy-MM-dd HH:mm:ss!^预约时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503307,//503307^GglyBeginTime not correct format,must be yyyy-MM-dd HH:mm:ss!^观光旅游开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503308,//503308^EffectiveTimeBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^开始生效时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503309,//503309^EffectiveTimeEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^结束生效时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503310,//503310^LastCallTime not correct format,must be yyyy-MM-dd HH:mm:ss!^上次通话时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503311,//503311^CallTime not correct format,must be yyyy-MM-dd HH:mm:ss!^拨打时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503312,//503312^CallStartTime not correct format,must be yyyy-MM-dd HH:mm:ss!^通话开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503313,//503313^CallEndTime not correct format,must be yyyy-MM-dd HH:mm:ss!^通话开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503314,//503314^AppointmentDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^预约开始时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503315,//503315^AppointmentDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^预约结束时间格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503316,//503316^beginActuralTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503317,//503317^endActuralTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503318,//503318^BeginCreatedTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503319,//503319^EndCreatedTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503320,//503320^BeginExpectTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_503321,//503321^endActuralTime not correct format,must be yyyy-MM-dd HH:mm:ss!^实际结束时间起格式不对，必须为yyyy-MM-dd HH:mm:ss
//    MSG_203422,//203422^Switch flag should be one of [0,1]^下线用户优惠状态控制开关必须为[0,1]其中之一
//    MSG_201802,//201800^Type should not be 0 or 1.^类型必须为0或者1
//    MSG_506809,//506809^UserType  not right,must be one of [1,2]!^用户类别只能为[1,2]其中之一
//    MSG_534007,//534007^Flag  not right,must be one of [-1,1]!^标识不正确的值,值只能为[-1,1]其中之一
//    MSG_211777,//211777^EndpointType  not right,must be one of [0,1,2,3,4]!^终端类型必须在值只能为[0,1,2,3,4]其中之一
//    MSG_504101,//504102^SendFlag  not right,must be one of [1,2,3,4]!^发送需求值只能为[1,2,3,4]其中之一
//    MSG_300436,//300436^EndpointType must in {0}!^终端类型必须在 {0}之中！
//    MSG_301503,//301503^UserType not right,must be U one of!^用户类型不正确的值,值只能为U其中之一
//    MSG_304339,//304341^Oldflag not right,must be 0,9 one of!^提案旧状态不正确的值,值只能为0,9其中之一
//    MSG_304338,//304342^Newflag not right,must be -3,2,9 one of!^提案新状态不正确的值,值只能为-3,2,9其中之一
//    MSG_304337,//304342^Newflag not right,must be -3,2 one of!^提案新状态不正确的值,值只能为-3,2其中之一
//    MSG_300827,//300827^BankFlag not right,must be 0,1,9,-9 one of!^银行有效标识不正确的值,值只能为0,1,9,-9其中之一
//    MSG_300833,//300834^Catalog not right,must be 0,1,2,3,4,5,6,7,8 one of!^账户类别不正确的值,值只能为0,1,2,3,4,5,6,7,8其中之一
//    MSG_304336,//304337^Oldflag not right,must be 0,2,9 one of!^提案旧状态不正确的值,值只能为0,2,9其中之一
//    MSG_304334,//304338^Newflag not right,must be -4,3 one of!^提案新状态不正确的值,值只能为-4,3其中之一
//    MSG_300826,//300827^BankFlag not right,must be 0,1,9,-9 one of!^银行有效标识不正确的值,值只能为0,1,9,-9其中之一
//    MSG_301111,//301111^LevelId not right,must be L001,L002,L003,C001,L004,L005 one of!^会员等级ID不正确的值,值只能为L001,L002,L003,C001,L004,L005其中之一
//    MSG_304348,//304349^Oldflag not right,must be 0 one of!^提案旧状态不正确的值,值只能为0其中之一
//    MSG_205301,//205304^IntegralAction not right,must be[ADD,SUB,RET,CLE] one of!^操作不正确的值,值只能为[ADD,SUB,RET,CLE]其中之一
//    MSG_310403,//310404^otpType not right,must be 0,1 one of!^操作类型不正确的值,值只能为 0,1 其中之一
//    MSG_309804,//309804^flag  not right,must be 2,-3  one of!^状态不正确的值,值只能为 2,-3 其中之一
//    MSG_309805,//309805^CallFlag  not right,must be 1,-1  one of!^电话状态不正确的值,值只能为 1,-1 其中之一
//    MSG_307024,//307024^Login Flag must 0 or 1^激活状态值只能为0或者1
//    MSG_509304,//509304^Type not right,must be 1,2,3,4 one of!^推荐类型不正确的值,值只能为1,2,3,4其中之一
//    MSG_366008,//366008^Newflag not right,must be -3, 1, 2, 9 one of!^提案新状态不正确的值,值只能为-3, 1, 2, 9其中之一
//    MSG_366007,//366007^TYPE not right,must be [01,02,03,04,05,06,07]^操作类型不正确的值,值只能为[01,02,03,04,05,06,07]其中之一!
//    MSG_680005,//680005^loginType not right,must be 1-3 one of!^匹配类型不正确,必须为1或2或3
//    MSG_306117,//306117^GameDsp not right,must be 0,1 one of!^DSP游戏不正确的值,值只能为0,1其中之一
//    MSG_306118,//306118^GameDyj not right,must be 0,1 one of!^DYJ游戏不正确的值,值只能为0,1其中之一
//    MSG_306119,//306119^GameBbin not right,must be 0,1 one of!^BBIN游戏不正确的值,值只能为0,1其中之一
//    MSG_306120,//306120^GameHg not right,must be 0,1 one of!^HG游戏不正确的值,值只能为0,1其中之一
//    MSG_306121,//306121^GameEa not right,must be 0,1 one of!^EA游戏不正确的值,值只能为0,1其中之一
//    MSG_306122,//306122^GameKeno not right,must be 0,1 one of!^KENO游戏不正确的值,值只能为0,1其中之一
//    MSG_306128,//306128^GameIpm not right,must be 0,1 one of!^IPM游戏不正确的值,值只能为0,1其中之一
//    MSG_306129,//306129^GameBg not right,must be 0,1 one of!^BG游戏不正确的值,值只能为0,1其中之一
//    MSG_306130,//306130^GameCw not right,must be 0,1 one of!^CW游戏不正确的值,值只能为0,1其中之一
//    MSG_306131,//306131^GameAg not right,must be 0,1 one of!^AG游戏不正确的值,值只能为0,1其中之一
//    MSG_306123,//306123^Kind1 not right,must be 0,1 one of!^体育类型不正确的值,值只能为0,1其中之一
//    MSG_306124,//306124^Kind3 not right,must be 0,1 one of!^视讯类型不正确的值,值只能为0,1其中之一
//    MSG_306125,//306125^Kind5 not right,must be 0,1 one of!^电子游艺类型不正确的值,值只能为0,1其中之一
//    MSG_306126,//306126^Kind7 not right,must be 0,1 one of!^对战类型不正确的值,值只能为0,1其中之一
//    MSG_306127,//306127^Kind4 not right,must be 0,1 one of!^K8类型不正确的值,值只能为0,1其中之一
//    MSG_308209,//308209^RebateMode not right,must be 1,2,3 of one!^洗码方式值不正确，必须是1,2,3之一
//    MSG_300903,//300903^Action not right,must be ADD,SUB one of!^操作类型不正确的值,值只能为ADD,SUB其中之一
//    MSG_304504,//304505^Action not right,must be IN,OUT one of!^操作类型不正确的值,值只能为IN,OUT其中之一
//    MSG_300401,//300401^RequestType  not right,must be one of [{0}]!^模版对应的提案类型只能为[{0}]其中之一
//    MSG_304510,//304513^ApTransferType not right,must be A,B one of!^AP操作类型不正确的值,值只能为A,B其中之一
//    MSG_304405,//304406^Newflag not right,must be 2,-2 one of!^新转账状态不正确的值,值只能为2,-2其中之一
//    MSG_310101,//310101^Newflag not right,must be 8,-8,-2 one of!^新转账状态不正确的值,值只能为8,-8,-2其中之一
//    MSG_317110,//317110^TransferCode must in 111514,111515!^转账代号必须在111514,111515！
//    MSG_305153,//305153^NewFlag value must in [-3,-2,-1,2,9]^New FLag值必须在[-3,-2,-1,2,9]范围内
//    MSG_305155,//305155^Approve Account Exchange request failed,please check value of old flag^审批提案失败,请检查待审提案状态是否正确
//    MSG_304832,//304833^Value of Catalog must one of [0,1,2,3,4,5,6,7,8]!^取款类别必须在[0,1,2,3,4,5,6,7,8]之中!
//    MSG_304395,//304395^New Flag value must in [-3,-1,1,2,0,-2,9]^New FLag值必须在[-3,-1,1,2,0,-2,9]范围内
//    MSG_304394,//304394^Approve withdrawal request failed,please check value of old flag^审批提案失败,请检查待审提案状态是否正确
//    MSG_305305,//305305^Value of Same Flag must in[0,1]!^取款银行备份状态值必须在[0,1]之间!
//    MSG_336013,//336013^audit flag must refuse or approve!^审批的状态必须为拒绝或者审批通过！
//    MSG_337108,//337108^flag must in 1,2!^状态必须在1,2之中！
//    MSG_337113,//337113^SupportMulti must in 1,2!^是否支持多笔转入必须在1,2之中！
//    MSG_334939,//334944^Flag not right,must be 0,1 one of!^状态值不正确,值只能为0,1其中之一
//    MSG_334911,//334912^Type must one of [1,2]^操作类型值必须在[1,2]之中
//    MSG_334916,//334916^Flag must one of [1,2,3]^记录状态值必须在[1,2,3]之中
//    MSG_201332,//201332^IsHighPercent  not right,must be one of [0,1]!^当月单个会员佣金高占比标识(50%及以上)值只能为[0,1]其中之一
//    MSG_304346,//304347^Action  not right,must be one of [ADD,SUB]!^调整类型值只能为[ADD,SUB]其中之一
//    MSG_311509,//311509^QueryType  not right,must be one of [1,2]!^查询类型不正确的值,值只能为[1,2]其中之一
//    MSG_311511,//203712^agentLevel must in (0,1,2,3)^代理级别必须为0,1,2,3
//    MSG_311506,//311506^Opera Type must in[0-auto,1-manual]!^修改类型必须为0-自动或者1-手动!
//    MSG_203712,//203712^agentLevel must in (0,1,2,3)^代理级别必须为0,1,2,3
//    MSG_802010,//802001^infFlag must in [1,2,3]!^infFlag必须在[1,2,3]之中！
//    MSG_205901,//205901^otpType  not right,must be one of [0]!^操作类型值只能为[0]其中之一
//    MSG_505106,//505106^ShowType not right,must be one of [0,1]!^显示类型值只能为[0,1]其中之一
//    MSG_999013,//999013^conditionType  not right,must be one of [1,2,3]!^拨打条件类型值只能为[1,2,3]其中之一
//    MSG_999014,//999015^callMode  not right,must be one of [1,2]!^拨打方式值只能为[1,2]其中之一
//    MSG_506808,//506808^infFlag  not right,must be one of [1,2,3,4]!^调用接口的标识值只能为[1,2,3,4]其中之一
//    MSG_506812,//506812^Line type not right,must be one of [1,2,3]!^专属专线类型值只能为[1,2,3]其中之一
//    MSG_601505,//601505^ReceiveFlag  not right,must be one of [-1,2]!^消息状态值只能为[-1,2]其中之一
//    MSG_300404,//300404^Enabled  not right,must be one of [{0}]!^状态值只能为[{0}]其中之一
//    MSG_506807,//506808^urlType  not right,must be one of [1,2]!^链接地址类别只能为[1,2]其中之一
//    MSG_403408,//403408^optType  not right,must be one of [0,1]!^操作类型值只能为[0,1]其中之一
//    MSG_403007,//403007^Flag  not right,must be one of [0,1]!^有效标识值只能为[0,1]其中之一
//    MSG_600223,//600223^ModifyType not right,must be 1,2,3,4,5,6,7,8,9,10,11,12,13 one of!^统计类别不正确的值,值只能为1,2,3,4,5,6,7,8,9,10,11,12,13其中之一
//    MSG_403006,//403007^LevelType  not right,must be one of [0,1]!^星级类型只能为[0,1]其中之一
//    MSG_304333,//304338^actType not right,must be 1-11 one of!^修改类型不正确的值,值只能为1到11其中之一
//    MSG_307812,//307812^LockFlag not right,must be 0,1 one of!^锁定标志不正确的值,值只能为0,1其中之一
//    MSG_301203,//301203^UserType not right,must be U,S one of!^用户类型不正确的值,值只能为U,S其中之一
//    MSG_301214,//301214^parent must is partner or agent!^上线会员必须为代理或合作伙伴
//    MSG_335904,//335904^flag not right,must be -9,-1 one of!^转账状态不正确的值,值只能为-9,-1其中之一
//    MSG_300217,//300217^ApplySite not right,must be 1,2,3,4,5,6 one of!^前后台提交标识不正确的值,值只能为1,2,3,4,5,6其中之一
//    MSG_300218,//300218^ApplyType not right,must be 1,2 one of!^提交类型不正确的值,值只能为1,2其中之一
//    MSG_300219,//300219^ApproveType not right,must be 1,2 one of!^审批类型不正确的值,值只能为1,2其中之一
//    MSG_300221,//300221^DateRange not right,must be 1d,1w,1m one of!^时间跨度不正确的值,值只能为1d,1w,1m其中之一
//    MSG_300223,//300223^CreditType not right,must be 0,1 one of!^额度类型不正确的值,值只能为0,1其中之一
//    MSG_306102,//306102^catalog not right,must be {0} one of!^归属地验证类型不正确的值,值只能为{0}其中之一
//    MSG_307410,//307410^isLocked not right,must be 0,1 one of!^锁定状态不正确的值,值只能为0,1其中之一
//    MSG_400409,//400409^TimeStatus not right,must be 0,1,6,12,15,7,90 one of!^时间标识不正确的值,值只能为0,1,6,12,15,7,90其中之一
//    MSG_335807,//335807^TransCode not right,must be 100100, 100101, 100102, 100103 one of!^额度代号不正确的值,值只能为100100, 100101, 100102, 100103其中之一
//    MSG_503411,//503411^Typecode not right,must be [{0}] one of!^短信类型不正确的值,值只能为[{0}]其中之一
//    MSG_503412,//503412^DateType not right,must be [LASTLOGIN,CREATEDATE] one of!^搜索类型不正确的值,值只能为[LASTLOGIN,CREATEDATE]其中之一
//    MSG_825017,//825017^RelationType not right,must be 0,1 one of!^关系类型不正确的值,值只能为0,1其中之一
//    MSG_202727,//202727^period Relation not right,must be 0,1,2 one of!^关系符不正确的值,值只能为0,1,2其中之一
//    MSG_302512,//302512^Flag not right,must be 0,1 one of!^有效标识不正确的值,值只能为 0,1 其中之一
//    MSG_302514,//302514^ApplyType not right,must be 0,1,2 one of!^自动提交标识不正确的值,值只能为 0,1,2 其中之一
//    MSG_302515,//302515^ApproveType not right,must be 0,1,2 one of!^自动审批标识不正确的值,值只能为 0,1,2 其中之一
//    MSG_302516,//302516^CreditType not right,must be 0,1 one of!^货币类型不正确的值,值只能为 0,1 其中之一
//    MSG_302517,//302517^SpecialFlag not right,must be 0,1 one of!^特殊标示不正确的值,值只能为 0,1 其中之一
//    MSG_201798,//201800^Sex not right,must in one of M,F.^性别不正确的值,值只能为M,F其中之一.
//    MSG_201754,//201754^Default Email Flag not right,must one of  0 and 1.^开户邮件默认不正确的值,值只能为0,1其中之一.
//    MSG_201755,//201755^Default SMS Flag not right,must one of  0 and 1.^开户短讯默认不正确的值,值只能为0,1其中之一.
//    MSG_201756,//201756^Default Message Flag not right,must one of  0 and 1.^开户站内消息默认不正确的值,值只能为0,1其中之一.
//    MSG_202157,//202158^IsMarket not right,must in one of 0,1.^市场部标识值不正确,只能为0,1其中之一.
//    MSG_503610,//503610^Flag not right,must in one of -1,0,1.^flag值只能为-1,0,1其中之一.
//    MSG_205805,//205805^Symbol not right,must be one of [<,>,<=,>=,=,!= ]!^积分数量查询比较符格式不正确,格式必须为！[<,>,<=,>=,=,!= ] 其中之一
//    MSG_380103,//380103^Bond type shouldn't null and must in 1,2!^绑定类型不允许为空，必须在1,2 之中！
//    MSG_301103,//301103^UserType must in 1,2,3,4,5!^用户类型必须在1,2,3,4,5！
//    MSG_230107,//2130107^no agent exist^找不到该代理对应信息
//    MSG_700901,//700901^WSQueryTMCase should not be empty!^电销名单查询不能为空不能为空!
//    MSG_503400,//503400^WSActivityCompetition  should not be empty!^WSActivityCompetition不能为空!
//    MSG_501233,//51233^taskNo cannot be empty!^订单编号不能为空值!
//    MSG_502235,//52235^taskName cannot be empty! ^ 任务名不能为空
//    MSG_502236,//52236^ConditionStartTime cannot be empty! ^ 条件起始时间不能为空
//    MSG_502237,//52237^ConditionEndTime cannot be empty! ^ 条件结束时间不能为空
//    MSG_502243,//52243^taskStartTime cannot be empty! ^任务开始时间不能为空
//    MSG_502244,//52243^taskEndTime cannot be empty! ^任务结束时间不能为空
//    MSG_700900,//700901^WSQueryCdrInfoLog should not be empty!^电销名单查询不能为空不能为空!
//    MSG_507876,//57876^type cannot be empty!^类型不能为空值
//    MSG_501234,//51234^CreateStyle cannot be empty!^创建方式不能为空值!
//    MSG_507875,//57876^type only can be 1 or 2!^类型只能为1,2其中之一
//    MSG_506799,//506802^Line Config Record does not exist!^专属链接配置不存在
//    MSG_522008,//522008^Cannot find customer info  for this loginName^找不到该登录名的客户信息
//    MSG_502240,//52241^taskPeriod cannot be empty! ^任务执行不能为空
//    MSG_502239,//52242^taskPeriodUnit cannot be empty! ^任务执行间隔不能为空
//    MSG_502238,//52239^synchronizeFlag cannot be empty! ^ 是否同步条件不能为空
//    MSG_502231,//52240^creator cannot be empty! ^ 创建者不能为空
//    MSG_501229,//52241^specifyLoginName cannot be empty!^手动上传名单不能为空值!
//    MSG_501228,//52241^specifyLoginName is duplicated!^手动上传名单不能为重覆!
//    MSG_501227,//51235^CreateStyle is not manual, but have manualCust data!^任务创建方式不是手工订单，但是有手动上传名单
//    MSG_501226,//51335^CreateStyle is manual, conditions and manualCust can't both be empty!^任务创建方式为手工订单，手动上传名单 & 条件不能同时为空值
//    MSG_501225,//52243^cannot find taskNo data!^找不到该笔订单号数据
//    MSG_501224,//52267^excludeTaskNo cannot be empty!^踢除名单编号不能为空值!
//    MSG_501223,//52259^excludeTaskNo is duplicated!^踢除名单有重覆!
//    MSG_501222,//52258^conditionType is not valid!^条件类型不符合!
//    MSG_501221,//52260^conditionType is duplicated!^条件有重覆!
//    MSG_501220,//52260^conditionStart or conditionEnd cannot be empty!^起始金额或结束金额不能为空值
//    MSG_501219,//52261^Need to query DataCenter data, conditionStartTime cannot small than now 60 days!^查询条件包含数据中心数据，条件开始日期不能小於当前时间60天!
//    MSG_501218,//56672^duplicate condition, date, exclude already exist!^已存在的配置有重覆的条件, 日期和踢除名单!
//    MSG_500011,//500000^query can not be null^query对象不能为空
//    MSG_829001,//829001^WSPromotionRequests should not be empty!^优惠提案信息不能为空!
//    MSG_402201,//402201^WSProductRateConfig should not be empty!^产品游戏洗码配置信息不能为空!
//    MSG_502217,//52244^longTimeNoBetDayStart cannot be empty^久未投注天数不能为空值!
//    MSG_502216,//52244^longTimeNoBetDayEnd cannot be empty^久未投注天数不能为空值!
//    MSG_502215,//52243^taskNo already exist!^该笔订单号数据已存在
//    MSG_301417,//301417^Promotion Config ID  not exist or is disable!^优惠配置ID不存在或已失效
//    MSG_334902,//334902^Not found config, cannot do transfer in!^未设置配置，暂时无法转入！
//    MSG_334900,//334900^Cannot transfer in!^暂时无法转入！
//    MSG_334000,//334001^the transfer type must IN or OUT!^转账类型只能是IN或OUT
//    MSG_334104,//334104^{0} already existing calculating transfer records!^{0}已存在正在计息的转账记录！
//    MSG_334103,//334103^LoginName {0} not exist!^登录名{0}不存在！
//    MSG_334102,//57876^object cannot be empty^物件不能为空值!
//    MSG_334115,//334115^transfer config not exist!^转账配置不存在！
//    MSG_334112,//334112^transfer amount should not more than {0}!^转账金额必须超过{0}！
//    MSG_334113,//334113^transfer amount should not less than the balance!^转账金额不能超过账户余额！
//    MSG_402901,//402901^WSProductGame should not be empty!^产品游戏关联信息不能为空!
//    MSG_334101,//334101^YebCredit should more than zero!^过夜利息总额不能小于0!
//    MSG_403301,//403301^WSProductConstants should not be empty!^配置信息不能为空!
//    MSG_403401,//403401^WSFavoritesGame should not be empty!^游戏收藏信息不能为空!
//    MSG_334005,//334005^the billno not exist!^计息单号不存在！
//    MSG_201302,//201303^CustomerId {0}  not exist!^会员ID({0})不存在
//    MSG_334100,//334101^YebCredit should not be empty!^过夜利息总额不能为空!
//    MSG_534003,//534003^Interest Log[{0}] has already transfer out^利息[{0}]已经存在转出记录
//    MSG_206003,//206003^Agent LoginName {0} is not exist!^代理账号[{0}]不存在
//    MSG_201110,//201110^No Commission to approval!^没有待审批的佣金
//    MSG_680003,//680003^refId does not existed^单号不存在
//    MSG_203705,//203705^WSQueryLoginLogs  should not be empty!^查询条件不能为空!
//    MSG_336002,//336002^Request Records not exist!^转账请求不存在
//    MSG_301910,//301910^Could not apply promotion, because the promotion is expired!^不在活动时间范围内不能申请优惠！
//    MSG_301723,//301723^Customer not belong the special promotion.^用户不属于该特殊优惠
//    MSG_306301,//306301^WSBetamountLevelConfig should not be empty!^WSBetamountLevelConfig不能为空!
//    MSG_306501,//306501^WSXmSet shouldn't be empty!^WSXmSet不能为空！
//    MSG_336018,//336018^WSYebConfig not exist!^yeb配置不存在
//    MSG_336010,//336010^confirmBy not exist!^确认人不存在
//    MSG_302911,//302911^Can not load xm config.^无正确的洗码配置.
//    MSG_302912,//302912^xm request currency  is not equals with the currency witch is load from db.^洗码提案币种和后端校验的币种不一致.
//    MSG_302908,//304912^xm request not exist.^洗码提案不存在.
//    MSG_304916,//304923^xm request have disposed.^洗码提案已经处理过.
//    MSG_304913,//304913^Rebate Request Failed To Approve!^洗码审批失败.
//    MSG_302915,//302915^xm request  amount is not correct.^洗码金额计算错误.
//    MSG_302914,//302914^valid xm amount is not enough,can not less than zero.^可洗金额不足,不能小于0.
//    MSG_701088,//701088^Query wallet reduceBetAmount exception!^查询新钱包优惠流水出现异常!
//    MSG_502368,//502368^Can not find TM Parent Customer^无法找到电销上级客户
//    MSG_334945,//334945^WSYebRateConfig should not be empty!^参数对象不能为空!
//    MSG_337000,//337000^WSYebConfig should not be empty!^配置对象不能为空
//    MSG_301394,//301394^Less than 0 customer level can not rebate!^产品已过滤0星级洗码
//    MSG_304910,//304910^customers is not exist^用户不存在
//    MSG_308301,//308301^requestIds is not empty!^requestIds 不能为空！
//    MSG_308302,//308302^newFlag is not empty!^newFlag 不能为空！
//    MSG_304911,//304934^xmConfig does not existed!^洗码配置不存在
//    MSG_305104,//305104^RebateDatails shouldn't be empty!^洗码分类详情信息不能为空
//    MSG_305107,//305107^xm rate not equals.^洗码比率和配置不相符。
//    MSG_305101,//305101^category must configure xmconfig details.^大类洗码必须配置子类洗码配置。
//    MSG_305100,//305104^xm classfly not belong to category.^xm分类不属于该洗码大类.
//    MSG_305201,//305201^WSCustBankBackup should not be empty!^会员取款银行备份信息不能为空!
//    MSG_304999,//304999^xm flag must be enabled^洗码配置对应数据中心的游戏大类不能为空
//    MSG_304998,//304999^xm flag must be enabled!^游戏类别洗码设置已设置为禁用
//    MSG_305102,//305102^exit the same xm configure.^存在相同的洗码配置。
//    MSG_305106,//305106^exist repeat xmType in xmdetails.^洗码详情存在重复的洗码类型.
//    MSG_308571,//308571^wsCustomer can not be null^wsCustomer不能为空
//    MSG_304899,//304901^WSXMRequests is not empty!^洗码提案信息 不能为空！
//    MSG_390001,//390001^customerParam shouldn't empty!^会员参数对象不能为空
//    MSG_802000,//802001^vo  should not be empty!^vo不能为空
//    MSG_337700,//337700^WsQueryYebSum shouldn't be null!^查询计息汇总对象不能为空
//    MSG_305849,//305849^Multi Currency Login Name does not existed^主账号不存在
//    MSG_201758,//201758^Partner Customer Id can not be null^Partner Customer Id 不能为空
//    MSG_304926,//304928^Failed to Create XM Request^创建洗码天失败
//    MSG_305848,//305849^Multi Currency Login Name does not existed^主账号不存在
//    MSG_202338,//202338^Language must be not empty!^会员语言信息不能为空！
//    MSG_205401,//205401^WSSpecilLoginLog should not be empty!^特殊会员登录信息不能为空!
//    MSG_334908,//334908^Reference Id does not existed!^关联单号不存在!
//    MSG_202001,//202001^WSFinger  should not be empty!^指纹讯息不能为空
//    MSG_826122,//826122^Request Id not exist!^提案ID不存在！
//    MSG_202401,//202401^WSCustomersSMSConfigs should not be empty!^会员短信配置不能为空!
//    MSG_300901,//300901^WSModifyCreditRequests should not be empty!^修改会员额度提案信息不能为空!
//    MSG_304965,//304965^Sub Account Name does not existed^子账号不存在
//    MSG_334928,//334928^Credit Line Log does not existed!^信用额度记录不存在!
//    MSG_312001,//312001^WSOnLinePayment should not be empty!^在线支付信息不能为空!
//    MSG_831004,//831004^Cannot find Vicious Appeal Info^没有找到相应的恶意投诉信息
//    MSG_503198,//503200^WSQueryActivityConstant  should not be empty!^WSQueryActivityConstant不能为空!
//    MSG_202000,//202001^LoginWebRequest can not be null^LoginWebRequest不能为空
//    MSG_202005,//202006^Account is not exist,please enter again^游戏帐户不存在，请重新输入
//    MSG_202007,//202007^wrong old pwd,please enter again^旧密码错误，请重新输入
//    MSG_200701,//200701^WSQueryDepositTrans should not be empty!^额度记录查询参数不能为空!
//    MSG_202101,//202101^LoginLogEntity can not be null^LoginLogEntity不能为空
//    MSG_304593,//304593^Init referenceId failed!^生成单号失败！
//    MSG_304590,//304590^Invalid Action!^无效的Action！
//    MSG_209001,//209001^wsCustomerMatch can not be null^匹配对象参数不能为为空
//    MSG_201299,//201301^WSAgentRelation is not null!^WSAgentRelation不能为空
//    MSG_304606,//304611^ReferenceId not exist!^转帐ID不存在
//    MSG_304605,//304612^Transfer customer not exist!^转账会员不存在！
//    MSG_300537,//300537^Bank account 1 does not exist!^Bank account 1不存在!
//    MSG_300538,//300537^Bank account 2 does not exist!^Bank account 2不存在!
//    MSG_300801,//300801^WSQueryCustomersBank should not be empty!^校验银行卡号重复参数不能为空!
//    MSG_203515,//203515^currency dealer flag  should not be empty!^币商标识不能为空!
//    MSG_202166,//202166^depositAutoPlatform should not be empty!^自动进厅标识不能为空！
//    MSG_202229,//202229^Default account ID cannot be empty!^默认账号标识不能为空
//    MSG_309001,//309001^WSApproveTourReservation shouldn't be empty!^批准对象wsApproveTourReservation不能为空！
//    MSG_200702,//200701^WSQueryCreditLogs can not be null^额度记录查询参数不能为空
//    MSG_306142,//306142^WSQueryCustomerLevelConfig shouldn't be empty!^WSQueryCustomerLevelConfig不能为空!
//    MSG_306143,//306143^WSQueryCustomersBetRecords shouldn't be empty!^WSQueryCustomersBetRecords不能为空!
//    MSG_200900,//200900^WSCreditLogs shouldn't be null!^修改贵宾厅额度对象不能为空！
//    MSG_309005,//309005^ID not exist!^ID不存在！
//    MSG_317000,//317001^WSCreditTransfer should not be empty!^额度转换信息不能为空!
//    MSG_305154,//305154^Please check value of New Flag^请检查New Flag值是否正确
//    MSG_308901,//308901^WSTourReservation shouldn't be empty!^WSTourReservation不能为空！
//    MSG_203107,//203107^Cannot find Record With ReferenceId ^ReferenceId对应的记录不存在
//    MSG_309604,//309604^Modify Phone RequestID not exist!^修改会员电话提案ID不存在！
//    MSG_523115,//523115^WsCustomersExt cannot be null!^扩展信息对象不能为空!
//    MSG_523116,//523116^Switch flag cannot be null!^下线用户优惠状态控制开关不能为空
//    MSG_509312,//509312^AccountNameBy not exist!^被推荐人账号不存在！
//    MSG_309301,//309301^wsOtherRequestss should not be empty!^其它类提案信息不能为空!
//    MSG_300799,//300801^WSModifyBankRequests should not be empty!^修改会员银行提案信息不能为空!
//    MSG_309315,//309315^New Agent not exist!^新代理不存在！
//    MSG_304352,//304360^Other RequestID not exist!^其他类提案ID不存在！
//    MSG_301271,//301301^WSModifyEmailRequests should not be empty!^修改会员邮箱提案信息不能为空!
//    MSG_301269,//303508^WSQueryModifyEmailRequests shouldn't be empty!^查询修改会员邮箱对象不能为空！
//    MSG_303508,//303508^query shouldn't be empty!^查询对象不能为空！
//    MSG_301101,//301101^WSModifyAccountRequests should not be empty!^修改会员状态提案信息不能为空！
//    MSG_306001,//306001^WSBetamountLevelConfig should not be empty!^WSBetamountLevelConfig不能为空!
//    MSG_300822,//300822^CustomerBankId not exist!^会员银行ID不存在！
//    MSG_305131,//305131^Source Login Name does not existed^转出账户不存在
//    MSG_305132,//305132^Target Login Name does not existed^转入账户不存在
//    MSG_500100,//500100^ModifyAccountRequests must not null!^修改账户状态对象不能为空！
//    MSG_304332,//304339^Modify Phone RequestID not exist!^修改会员电话提案ID不存在！
//    MSG_300798,//300801^WSModifyBankRequests should not be empty!^修改会员银行提案信息不能为空!
//    MSG_201201,//201201^WSCustomersBank should not be empty!^会员银行信息不能为空！
//    MSG_301100,//301101^WSModifyLevelRequests should not be empty!^修改会员等级提案信息不能为空!
//    MSG_304351,//304351^Modify Level RequestID not exist!^修改会员等级提案ID不存在！
//    MSG_205300,//205301^WSIntegralRequest should not be empty!^积分修改信息不能为空!
//    MSG_205314,//205314^No Integral Request to reject^没有提案审批
//    MSG_530001,//530001^Main customer info shouldn't null!^主会员信息不能为空！
//    MSG_530101,//530101^Main RealNameRequest info shouldn't null!^主修改真实姓名提案信息不能为空！
//    MSG_308579,//308579^Create trial Account failed^创建试玩用户失败
//    MSG_700899,//700901^WSQueryTMAssignment should not be empty!^电销名单查询不能为空不能为空!
//    MSG_200321,//200321^queryCustomerDownline cannot be null!^下线用户查询条件不能为空!
//    MSG_305300,//305301^withdrawalRequest Cannot be null!^取款提案信息不能为空!
//    MSG_305304,//305304^Withdrawal Request ID doest not existed!^取款提案ID不存在!
//    MSG_308577,//308579^Create Real Account failed^创建真实用户失败
//    MSG_306101,//306101^WSCustomerRecommend can not be null^WSCustomerRecommend不能为空
//    MSG_203602,//203602^parent does not exist!^会员上级不存在
//    MSG_306116,//306116^WSCallBackRecords shouldn't be empty!^WSCallBackRecords不能为空！
//    MSG_306115,//306115^QueryCallBackRecords shouldn't be empty!^QueryCallBackRecords不能为空！
//    MSG_306144,//306144^WSCustomersBetRecords shouldn't be empty!^WSCustomersBetRecords不能为空!
//    MSG_306146,//306146^WSQuerySpecialXMConfig shouldn't be empty!^WSQuerySpecialXMConfig不能为空!
//    MSG_306145,//306145^WSCustomersBetRecords shouldn't be empty!^WSCustomersBetRecords不能为空!
//    MSG_500127,//50127^conditionType cannot be empty1^条件类别不能为空!
//    MSG_500126,//50126^taskNo cannot be empty1^任务编号不能为空!
//    MSG_301152,//301152^RequestId not exist!^单号不存在！
//    MSG_300201,//300201^wsPromotionConfig should not be empty!^优惠类型配置不能为空!
//    MSG_334851,//334851^The transfer not exist!^转账单不存在！
//    MSG_304941,//304941^rebate request must be not null!^洗码提案不能提交空对象！
//    MSG_304898,//304900^rebate request have mast be not null!^洗码提案不能为空！
//    MSG_403402,//403402^wsCustomers does not exist!^会员不存在
//    MSG_507008,//507008^dictId cannot be null!^字典数据不能为空!
//    MSG_500019,//500019^oldPwd should not be empty!^旧密码不能为空！
//    MSG_500020,//500020^Pwd should not be empty!^密码不能为空！
//    MSG_500022,//500022^Label Value can not be null.^标签内容不能为空！
//    MSG_500023,//500023^FacebookId should not be empty!^FacebookId不能为空！
//    MSG_500024,//500024^MatchValue should not be empty.^待匹配数据不能为空！
//    MSG_500025,//500025^CheckValue should not be empty!^敏感数据不能为空!
//    MSG_500026,//500026^ApTransferStatus can not be empty!^ap对战状态不允许为空!
//    MSG_500027,//500027^ApBank must not be empty!^银行卡不能为空!
//    MSG_500028,//500028^lastGame must not empty!^最后登录游戏不允许为空!
//    MSG_500029,//500029^Forum Flag must not be empty!^论坛标志不能为空!
//    MSG_500031,//500031^Bank Account Number should not be empty!^银行卡号不能为空!
//    MSG_500032,//500032^Virtual Account Name should not be empty!^虚拟银行帐号不能为空!
//    MSG_500033,//500033^Virtual Account No should not be empty!^虚拟银行卡号不能为空!
//    MSG_500034,//500034^Bank Account Name should not be empty!^银行帐号名不能为空!
//    MSG_500035,//500035^Bank Account Type should not be empty!^银行账号类型不能为空!
//    MSG_500036,//500036^Bank Account Country should not be empty!^银行国家不能为空!
//    MSG_500037,//500037^Bank Account City should not be empty!^银行城市不能为空!
//    MSG_500038,//500038^Bank Name should not be empty!^银行名称不能为空!
//    MSG_500039,//500039^Bank Priority Order should not be empty!^银行卡优先级不能为空!
//    MSG_500041,//500041^Type should not be empty!^活动类型不能为空!
//    MSG_500042,//500042^MatchTime should not be empty!^赛事时间不能为空!
//    MSG_500043,//500043^Competition should not be empty!^赛事内容不能为空!
//    MSG_500044,//500044^Team1 should not be empty!^队伍1不能为空!
//    MSG_500045,//500045^Team2 should not be empty!^队伍2不能为空!
//    MSG_500046,//500046^ID should not be empty!^ID不能为空！
//    MSG_500047,//500047^constantType should not be empty!^常量类型不能为空！
//    MSG_500048,//500048^ID should not be empty!^删除ID不能为空!
//    MSG_500049,//500049^content should not be empty!^常量内容不能为空！
//    MSG_500050,//500050^the game name shouldn't be empty!^游戏名称不能为空！
//    MSG_500051,//500051^the loginIp shouldn't be empty!^会员登陆IP不能为空！
//    MSG_500052,//500052^the domain name shouldn't be empty!^网站域名不能为空！
//    MSG_500053,//500053^tokenCode should not be empty!^tokenCode不能为空!
//    MSG_500054,//500054^LoginEndPointType should not be empty!^登录终端类型不能为空!
//    MSG_500055,//500055^mobileNo should not be empty!^电话号码不能为空!
//    MSG_500056,//500056^tokenType shouldn't empty!^tokenType不能为空!
//    MSG_500057,//500057^Ip Address should not be empty!^IP地址不能为空!
//    MSG_500058,//500058^Remarks should not be empty!^备注不能空!
//    MSG_500059,//500059^Phone can not be empty!^电话不能为空!
//    MSG_500060,//500060^Flag can not be empty!^状态码不能为空!
//    MSG_500061,//500061^ReferenceId should not be empty!^提案编号不能为空!
//    MSG_500062,//500062^Deposit Type should not be empty!^ 存款方式不可以为空
//    MSG_500063,//500063^Type should not be empty!^类型不能为空!
//    MSG_500064,//500064^Amount should not be empty!^转换的金额不能为空!
//    MSG_500065,//500065^requst id should not be empty!^requst id不能为空!
//    MSG_500066,//500066^Verify Function should not be empty!^验证方式不得为空值!
//    MSG_500067,//500067^Customer current email/Phone shouldn't be empty!^会员当前邮箱/电话不能为空！
//    MSG_500068,//500068^Verify Code should not be empty!^验证码不得为空值!
//    MSG_500069,//500069^Verify Type should not be empty or null check for activity_id if type is 12!^验证用途不得为空值或者不匹配活动检查逻辑!
//    MSG_500070,//500070^Verify Code Max Count should not be empty!^可验证次数不得为空值
//    MSG_500072,//500072^Created By should not be empty!^创建人不得为空值
//    MSG_500073,//500073^Verify Time should not be empty!^有效时间（分）不得为空值
//    MSG_500074,//500074^activity id By should not be empty!^activity id不得为空值
//    MSG_500075,//500075^Bound Email should not be empty!^会员绑定的邮箱不得为空值
//    MSG_500076,//500076^Signature should not be empty!^签名档不能为空!
//    MSG_500077,//500077^generate the verification code failed!^生成验证码失败！
//    MSG_500078,//500078^LoginName should not be empty!^查询绑定的手机或邮箱号对应的验证码时，用户名不能为空!
//    MSG_500079,//500079^RequestId should not be empty!^提案编号不能为空!
//    MSG_500080,//500080^Bond Old Context should not be empty!^旧绑定内容不能为空!
//    MSG_500081,//500081^Bond Context should not be empty!^绑定内容不能为空!
//    MSG_500082,//500082^Bond Type should not be empty!^绑定类别不能为空!
//    MSG_500083,//500083^Verify Code Id should not be empty!^验证码流水号不能为空!
//    MSG_500084,//500084^Same Accounts should not be empty^相同号码绑定账号不能为空!
//    MSG_500085,//500085^RealName should not be empty!^真实姓名不能为空!
//    MSG_500086,//500086^PriorityOrder should not be empty!^银行优先标识不能为空!
//    MSG_500087,//500087^vipTag should not be empty!^vip标签不能为空
//    MSG_500088,//500088^Main customer info shouldn't null!^主会员信息不能为空！
//    MSG_500089,//500089^IntegralType should not be empty!^积分类型不能为空!
//    MSG_500090,//500090^Destination should not be empty!^观光地点不能为空!
//    MSG_500091,//500091^DeparturePlace should not be empty!^起飞地点不能为空!
//    MSG_500092,//500092^Account Name should not be empty!^推荐人不能为空!
//    MSG_500093,//500093^Approve by should not be empty!^审核人不能为空!
//    MSG_500094,//500094^Customer Level should not be empty!^客户等级不能为空!
//    MSG_500095,//500095^Balance Type should not be empty!^额度类型不能为空!
//    MSG_500096,//500096^Balance should not be empty!^额度不能为空!
//    MSG_500097,//500097^Bill No should not be empty!^支付单号不能为空!
//    MSG_500098,//500098^ReferenceId should not be empty!^转账单号不能为空!
//    MSG_500099,//500099^Query exchange rate error {0}!^获取兑换比率失败！未配置兑换类型{0}
//    MSG_501100,//500100^withdrawal limit config error^每天取款限额未配置或者配置错误,请联系客服
//    MSG_500102,//500102^EndpointType should not be empty!^终端类型不能为空!
//    MSG_500103,//500103^confirmBy shouldn't empty!^确认人不能为空!
//    MSG_500104,//500104^createdBy should not be empty!^创建人不能为空!
//    MSG_500105,//500105^deletedBy should not be empty!^删除人不能为空!
//    MSG_500106,//500106^Code should not be empty!^配置代号不能为空!
//    MSG_500107,//500107^rate should not be empty!^利率不能为空!
//    MSG_500108,//500108^YearRate should not be empty!^年利率不能为空!
//    MSG_500109,//500109^MinAmount should not be empty!^最小存入金额不能为空!
//    MSG_500110,//500110^MinHours should not be empty!^最小计息小时数不能为空
//    MSG_500111,//500111^ConfigId should not be empty!^配置Id不能为空
//    MSG_500112,//500112^DimensionKey shouldn't be empty!^统计维度不能为空
//    MSG_500113,//500113^relationValue is not null^relationValue不能玩为空
//    MSG_500114,//500114^Trans Code can nnot be null^转账代码不能为空
//    MSG_500115,//500115^Btc Rate can nnot be null^BTC 汇率不能为空
//    MSG_500116,//500116^Btc Amount can nnot be null^BTC 金额不能为空
//    MSG_500117,//500117^The amount shouldn't be empty!^修改的额度不能为空！
//    MSG_500118,//500118^Status should not be empty!^状态不得为空值
//    MSG_500119,//500119^agentName is not null^代理账号不能为空
//    MSG_500120,//500120^agentLevel is not null^代理级别不能为空
//    MSG_500121,//500121^New Value cannot be null!^New Value不能为空!
//    MSG_500122,//500122^Opera Type cannot be null!^操作类型不能为空!
//    MSG_500123,//500123^Label ID cannot be null!^标签ID不能为空!
//    MSG_500124,//500124^Label Category not be null!^标签大类不能为空!
//    MSG_500125,//500125^StatisticsDate shouldn't be empty!^统计时间不能为空!
//    MSG_501126,//500126^creator/updater shouldn't empty!^创建/更新人不能为空！
//    MSG_501127,//500127^question type should not empty and !^问题类型不能为空!
//    MSG_500130,//500130^RelationValue is not null!^RelationValue不能为空!
//    MSG_500131,//500131^RelationType is not null!^RelationType不能为空!
//    MSG_500132,//500132^relationFlag is not null!^relationFlag不能为空!
//    MSG_500133,//500133^updateBy is not null!^更改人不能为空!
//    MSG_500134,//500134^relationId is not null!^relationId不能为空!
//    MSG_500135,//500135^FirstDepositDateBegin is not null!^首存查询开始时间不能为空!
//    MSG_500136,//500136^FirstDepositDateEnd is not null!^首存查询结束时间不能为空!
//    MSG_500137,//500137^Device ID cannot be null!^设备ID不能为空!
//    MSG_500138,//500138^TransactionCode cannot be null!^交易代码不能为空!
//    MSG_500139,//500139^Title shouldn't be empty!^标题不能为空!
//    MSG_500140,//500140^TextContent shouldn't be empty!^内容不能为空!
//    MSG_500141,//500141^messageId shouldn't be empty!^消息Id不能为空!
//    MSG_500142,//500142^sendStatus shouldn't be empty!^推送状态不能为空!
//    MSG_500143,//500143^sumDate should not be empty!^统计时间不能为空!
//    MSG_500144,//500144^endpointName should not be empty!^接口名不能为空!
//    MSG_500145,//500145^InvokerApp should not be empty!^调用应用不能为空!
//    MSG_500146,//500146^Last IP should not be empty!^最后访问IP不能为空!
//    MSG_500147,//500147^ReportType should not be empty!^统计的分类不能为空!
//    MSG_500148,//500148^accountNo should not be empty!^accountNo不能为空!
//    MSG_500149,//500149^Currency should not be empty!^货币类型不能为空!
//    MSG_500150,//500150^BondseqId should not be empty!^主键不能为空!
//    MSG_500151,//500151^Bond context shouldn't null!^绑定内容不允许为空!
//    MSG_500152,//500152^Credit Appeal Id should not be empty!^存取款进度核查记录ID不能为空!
//    MSG_500153,//500153^Bank ID should not be empty!^Bank ID不能为空!
//    MSG_500154,//500154^Amount should not be empty!^Amount不能为空!
//    MSG_500155,//500155^update TM_Parent,old TM_Parent must exist!^更改电销上级，原来用户电销上级必须存在!
//    MSG_500156,//500156^RequestType should not be empty!^RequestType不能为空!
//    MSG_500157,//500157^promotionType Id can not be null^优惠类型不能为空!
//    MSG_500158,//500158^Account Name By should not be empty!^被推荐人不能为空!
//    MSG_500159,//500159^RetCode cannot be null!^接口调用状态不能为空!
//    MSG_500160,//500160^alidateResult cannot be null!^验证结果不能为空!
//    MSG_500161,//500161^RefId should not be empty!^引用Id不能为空!
//    MSG_500162,//500162^xmType should not be empty!^洗码类型不能为空!
//    MSG_500163,//500163^functionDesc should not be empty!^functionDesc不能为空!
//    MSG_500164,//500164^functionCode should not be empty!^菜单编号不能为空!
//    MSG_500165,//500165^FunctionParentCode should not be empty!^父编号不能为空!
//    MSG_500166,//500166^FunctionLink should not be empty!^菜单链接不能为空!
//    MSG_500167,//500167^FunctionLevel should not be empty!^菜单层级不能为空!
//    MSG_500168,//500168^functionName should not be empty!^functionName不能为空!
//    MSG_500169,//500169^Task Detail Id should not be empty!^任务详情ID不能为空!
//    MSG_500170,//500170^TaskType should not be empty!^任务类型不能为空!
//    MSG_500171,//500171^Task Detail ID should not be empty!^任务详情ID不能为空!
//    MSG_500172,//500172^Task Operation Id should not be empty!^任务操作ID不能为空!
//    MSG_500173,//500173^Bound Phone should not be empty!^会员绑定的电话不得为空值!
//    MSG_500174,//500174^ConfigurationId should not be empty!^优惠配置ID不能为空!
//    MSG_500175,//500175^Telemarketer should not be empty!^Telemarketer 不能为空!
//    MSG_500176,//500176^Long Phone Number should not be empty!^Long Phone Number不能为空!
//    MSG_500177,//500177^Month should not be empty!^月份不能为空!
//    MSG_500178,//500178^CallSign should not be empty!^通话标识不能为空!
//    MSG_500179,//500179^AppointID should not be empty!^预约ID不能为空!
//    MSG_500180,//500180^VipModifyType should not be empty!^修改类型不能为空!
//    MSG_500181,//500181^TypeName should not be empty!^类型名不能为空!
//    MSG_500182,//500182^RefrenceId should not be empty!^关联单号不能为空!
//    MSG_500183,//500183^YebBillno should not be empty!^计息单号不能为空!
//    MSG_500184,//500184^Customers Remarks ID cannot be null!^客户备注ID不能为空!
//    MSG_500185,//500185^StartDate is not null!^域名开通时间不能为空!
//    MSG_999005,//999005^settingId cannot be match!^settingId找不到对应的拨打设置!
//    MSG_601103,//601103^Message didn't exist!^消息不存在!
//    MSG_820013,//820014^Job does not exist!^该Job不存在
//    MSG_700120,//700120^Failed to check the valid bet amount product constant.^查询有效投注额类型失败.
//    MSG_700122,//700122^Monthly salary discount configuration is not set.^月工资优惠配置未开启.
//    MSG_700123,//700123^Get monthly salary gift configuration information is empty.^获取月工资礼金配置信息为空.
//    MSG_300304,//300306^Id  should not be empty!^模版ID不能为空!
//    MSG_666000,//666001^WSQueryDmsBatchDetails is not be not^查询对象不能为空
//    MSG_305200,//305201^StockValue should not be empty!^创建月股票价值信息不能为空!
//    MSG_310115,//310115^Task No does not exist!^任务编号不存在!
//    MSG_501059,//50125^excludeTaskNo cannot be empty1^踢除订单号不能为空!
//    MSG_403400,//403402^ID does not exist!^产品常量配置不存在
//    MSG_403704,//403704^id not delete^产品常量配置不能删除
//    MSG_403002,//403002^Id does not exist!^关联信息不存在
//    MSG_402301,//402301^WSProducts should not be empty!^产品信息不能为空!
//    MSG_306202,//306202^WSBetamountLevelConfig ID not exist!^配置ID登录名不存在！
//    MSG_213005,//213005^Bond Record not exist!^绑定记录不存在
//    MSG_304327,//304335^Modify Credit RequestID not exist!^修改会员额度提案ID不存在！
//    MSG_305713,//305713^BankId should not exist!^存款银行ID不存在！
//    MSG_334891,//334891^The yeb not exist!^计息记单不存在！
//    MSG_303300,//303300^WSQueryModifyAccountRequests should not be null!^WSQueryModifyAccountRequests查询对象不能为空！
//    MSG_301213,//301213^ParentLoginName not exist!^上线会员不存在！
//    MSG_211601,//211601^WSCreateCreditLogs should not be empty!^WSCreateCreditLogs不能为空!
//    MSG_304350,//304364^WSDepositToGame not exist!^取款提案不存在
//    MSG_201348,//201348^Source Type should not be empty^资源类型不能为空
//    MSG_820028,//820028^Task Deatil does not existed !^Task Detail不存在!
//    MSG_320001,//320001^wsDepositToGame should not be empty!^游戏取款提案信息不能为空!
//    MSG_400408,//400408^FunctionId:{0} not exist!^功能菜单ID:{0}不存在
//    MSG_856006,//856006^ID can not be null^ID 不能为空
//    MSG_512001,//512001^Creator must not be empty^创建人不能为空!
//    MSG_201910,//201910^GroupId should not be empty!^GroupId 不能为空!
//    MSG_202335,//202338^Language must be not empty!^会员语言信息不能为空！
//    MSG_308569,//308569^产品默认币种DEFAULT_CURRENCY没有配置!^Please config Product Default Currency first!
//    MSG_308580,//308580^主账号登录名不能为空^Main Account Name can not be null
//    MSG_201778,//201778^Please config Constant TRIAL_ACCOUNT_PREFIX first^请先配置常量TRIAL_ACCOUNT_PREFIX
//    MSG_203798,//203798^promotionType  should not be empty!^优惠类型不能为空!
//    MSG_220123,//220123^customerId  should not be empty!^用户ID不能为空!
//    MSG_900002,//900002^IsRecommend  should not be empty!^推荐状态不能为空!
//    MSG_523102,//523102^mainAccountId should not empty!^mainAccountId不能为空！
//    MSG_523202,//523202^lastLoginIp should not empty!^lastLoginIp不能为空！
//    MSG_523148,//523148^Flag should not be empty.^Flag不能为空
//    MSG_523107,//523107^reserve1 can not be null!^预留信息不能为空！
//    MSG_523150,//523150^CurrentTime should not be empty^当前时间不能为空
//    MSG_304914,//304922^XM Rate should not be empty^洗码比例不能为空
//    MSG_304881,//304881^xmType can not be empty^洗码类型不能为空
//    MSG_830007,//830007^arbitrageurSource  should not be empty!^套利客来源不能为空!
//    MSG_701025,//701025^tmCaseId should not be empty!^tmCaseId不能为空!
//    MSG_701022,//701022^assignedBy should not be empty!^assignedBy不能为空!
//    MSG_701023,//701023^assignedTo should not be empty!^assignedTo不能为空!
//    MSG_701024,//701024^caseValidity should not be empty!^caseValidity不能为空!
//    MSG_700865,//700865^ReferenceId should not be empty!^ReferenceId不能为空!
//    MSG_211607,//211607^Channel  should not be empty!^渠道不能为空!
//    MSG_211608,//211608^MerchantNo  should not be empty!^商户号不能为空!
//    MSG_211614,//211614^Promotion Source Amount cannot be null^优惠前金额不能为空
//    MSG_506223,//506225^UpdateBy  should not be empty!^修改人不能为空!
//    MSG_201330,//201330^Agent commissionId  should not be empty!^佣金ID不能为空!
//    MSG_661102,//661102^pDictCode  should not be empty!^pDictCode不能为空!
//    MSG_507001,//507001^Dictionary Code  should not be empty!^字典编码不能为空!
//    MSG_507002,//507002^Dictionary Value(CN)  should not be empty!^字典值(中国)不能为空!
//    MSG_507003,//507003^Dictionary Value(EN)  should not be empty!^字典值(英语)不能为空!
//    MSG_507004,//507004^Dictionary ID  should not be empty!^序号不能为空!
//    MSG_506905,//506906^userName should not be empty!^客服不能为空!
//    MSG_334947,//334947^LastUpdateBy should not be empty!^更新人不能为空!
//    MSG_337201,//337201^ConfigId should not be empty!^配置Id不能为空!
//    MSG_334907,//334907^Reference Id should not be empty!^关联单号不能为空!
//    MSG_334925,//334925^Task No should not be empty!^流水编号不能为空!
//    MSG_681004,//681004^presentationCount should not be empty!^赠送次数不能为空!
//    MSG_820011,//820013^Job Id  should not be empty!^Job主键不能为空!
//    MSG_820016,//820016^Job Name  should not be empty!^Job 名称不能为空!
//    MSG_820017,//820017^Job Group  should not be empty!^Job群组不能为空!
//    MSG_820041,//820041^Job Status should not be empty!^Job状态不能为空!
//    MSG_308570,//308570^infFlag can not be null^infFlag不能为空
//    MSG_206098,//206104^ContributionMonth shouldn't be empty!^佣金月份不能为空!
//    MSG_201019,//201019^WeChatOpenId shouldn't empty!^WeChatOpenId不能为空！
//    MSG_202663,//202663^FacebookId shouldn't empty!^FacebookId不能为空！
//    MSG_305847,//305848^Multi Currency Login Name should not be empty^主账号不能为空
//    MSG_856007,//856007^Account can not be null^账户不能为空
//    MSG_856009,//856010^Type can not be null^账户类型不能为空
//    MSG_305897,//305897^Is Market can not be null^Is Market不能为空
//    MSG_300830,//300831^tokenCode should not empty^tokenCode不能为空
//    MSG_204307,//204307^the login ip should not be empty^登录IP不能为空
//    MSG_204306,//204306^the game name should not be empty^游戏名称不能为空
//    MSG_402418,//402418^Parent Id does not existed^上级ID不存在
//    MSG_202167,//202167^depositAutoPlatform should not be empty!^自动进厅标识不能为空！
//    MSG_300040,//300028^Wrong Recommend Code^推荐码不能为空值
//    MSG_306015,//306015^SourceDomain should not be empty!^域名不能为空!
//    MSG_306016,//306016^ChannelType should not be empty!^渠道类型不能为空!
//    MSG_510001,//501^firstIdType should not be empty!^firstIdType不能为空!
//    MSG_510002,//502^SecondIdType should not be empty!^SecondIdType不能为空!
//    MSG_510004,//503^firstName should not be empty!^firstName不能为空!
//    MSG_510009,//504^lastName should not be empty!^lastName不能为空!
//    MSG_510011,//505^BranchCode  should not be empty!^BranchCode不能为空!
//    MSG_510012,//506^Sex should not be empty!^Sex不能为空!
//    MSG_510014,//508^BirthDate should not be empty!^BirthDate不能为空!
//    MSG_510015,//509^Address should not be empty!^Address不能为空!
//    MSG_858408,//858408^Token Code should not empty^Token码不能为空
//    MSG_300501,//300501^IP Address cannot be null!^IP地址不能为空!
//    MSG_300504,//300502^Bank Account 1 cannot be null!^Bank Account 1不能为空!
//    MSG_300505,//300503^Bank Account 2 cannot be null!^Bank Account 2不能为空!
//    MSG_824027,//824027^CustomerLabel ID  should not be empty!^客户标签信息ID不能为空!
//    MSG_826024,//826024^Category  should not be empty!^标签大类不能为空!
//    MSG_204665,//204665^stock value should not be empty!^月股票价值不能为空!
//    MSG_700878,//700878^IsStandards  can not be empty!^IsStandards不能为空
//    MSG_700879,//700879^VipFlag  can not be empty!^VipFlag不能为空
//    MSG_700873,//700873^ReportMonth  can not be empty!^ReportMonth不能为空
//    MSG_700874,//700874^IsStandards  can not be empty!^IsStandards不能为空
//    MSG_204303,//204303^the domain name  should not be empty!^网站域名不能为空!
//    MSG_204309,//204309^the game name  should not be empty!^游戏名称不能为空!
//    MSG_204304,//204304^the loginIp should not be empty!^会员登陆IP不能为空!
//    MSG_204311,//204311^Login Ip can not be null^登录IP不能为空
//    MSG_605587,//605587^Result Text should nor empty^Result Text不能为空
//    MSG_999004,//999004^settingId  should not be empty!^ESMS拨打设置ID不能为空!
//    MSG_303529,//303529^Old Activated Status can not be null^旧激活状态不能为空
//    MSG_303528,//303528^Activated Status can not be null^激活状态不能为空
//    MSG_302345,//302345^last login ip is empty^最后登录ip不能为空！
//    MSG_302348,//302348^promotion is empty^优惠类型不能为空
//    MSG_824028,//824028^Achievement ID  should not be empty!^标签ID不能为空!
//    MSG_826023,//826023^Label Type should not be empty!^标签类型不能为空!
//    MSG_826025,//826025^Label Level should not be empty!^标签级别不能为空!
//    MSG_826026,//826026^Begin Time should not be empty!^标签开始时间不能为空!
//    MSG_826027,//826027^End Time should not be empty!^标签结束时间不能为空!
//    MSG_200012,//200012^Login IP  should not be empty!^登录IP不能为空!
//    MSG_120011,//120011^TransCode  should not be empty !^提交类型不能为空!
//    MSG_120012,//120012^EndPoint should not be empty !^提交来源不能为空!
//    MSG_200720,//200720^FailCount should not be empty!^连续失败次数不能为空!
//    MSG_522007,//522007^Keyword  should not be empty!^关键字不能为空!
//    MSG_522009,//522008^Min Id  should not be empty!^最小ID不能为空!
//    MSG_701018,//701018^Case Active Status should not be empty!^Case Active Status不能为空!
//    MSG_701019,//701019^Assigned By should not be empty!^Assigned By不能为空!
//    MSG_701020,//701020^caseDigStatus should not be empty!^caseDigStatus不能为空!
//    MSG_701021,//701021^caseStatus should not be empty!^caseStatus不能为空!
//    MSG_701029,//701029^caseOwner should not be empty!^caseOwner不能为空!
//    MSG_701031,//700130^The effective bet product constant is not set.^有效投注额类型产品常量沒配置.
//    MSG_606503,//606503^Status should not be empty!^状态不能为空!
//    MSG_301619,//301616^Channel  should not be empty!^渠道不能为空!
//    MSG_301622,//301617^MerchantNo  should not be empty!^商户号不能为空!
//    MSG_366108,//366108^ApproveBy  should not be empty!^审核人不能为空!
//    MSG_403632,//403632^Key  should not be empty!^键不能为空!
//    MSG_403633,//403633^Value  should not be empty!^值不能为空!
//    MSG_403634,//403634^Type  should not be empty!^类型不能为空!
//    MSG_700100,//700100^referenceId should not be empty!^referenceId不能为空！
//    MSG_305128,//305128^Rate Cannot be null!^汇率不能为空!
//    MSG_305139,//305139^Configuration of Transfer Out Minimum Amount does not existed^转出最小金额没有配置
//    MSG_203108,//203108^Amount should not be empty^提案金额不能为空
//    MSG_821010,//821010^Task Name  should not be empty!^任务名称不能为空!
//    MSG_821011,//821011^Task Type  should not be empty!^任务类型不能为空!
//    MSG_821014,//821013^Task Config Id  should not be empty!^任务配置ID不能为空!
//    MSG_821018,//821018^Promotion Config Id should be Integer!^优惠配置ID必须为整数
//    MSG_821019,//821019^Task Id should be Integer!^任务ID必须为整数
//    MSG_304924,//304920^Betting Amount should not be empty^投注额不能为空
//    MSG_304936,//304923^Rebate Mode should not be empty^洗码方式不能为空
//    MSG_203416,//203416^registerIP  should not be empty!^注册IP不能为空!
//    MSG_203420,//203420^registerIP  should not be empty!^登录IP不能为空!
//    MSG_503111,//503111^PlatId should not be empty!^平台ID不能为空!
//    MSG_503112,//503112^GameKind should not be empty!^游戏大类不能为空!
//    MSG_503113,//503113^BettingAmount should not be empty!^投注额不能为空!
//    MSG_303610,//303610^Approve By should not be empty^审批人不能为空
//    MSG_307037,//307038^Please set password first^请先设置密码
//    MSG_300829,//300832^tokenCode should not be empty!^tokenCode不能为空!
//    MSG_307036,//307036^Old Phone can not be null^旧电话不能为空
//    MSG_307015,//307015^Parameters can not be null^参数对象不能为空
//    MSG_305014,//305014^CustomerId cannot be null!^客户ID不能为空!
//    MSG_305189,//305189^Source Login Name can not be null^转出账户不能为空
//    MSG_506798,//506801^UrlConfg ID  should not be empty!^线路配置ID不能为空!
//    MSG_506797,//506802^ExclusiveUrl should not be empty!^专属链接地址不能为空!
//    MSG_403607,//403607^type should not be empty!^产品常量类型不能为空!
//    MSG_403618,//403618^key should not be empty!^产品常量主键id不能为空!
//    MSG_308908,//308908^Reservationtime should not be empty!^预订时间不能为空!
//    MSG_311001,//311001^ids should not be empty^记录ID不能为空
//    MSG_610208,//610208^CroupBy should not be empty !^分组统计条件不能为空!
//    MSG_601502,//601502^ReceiveFlag  should not be empty!^消息状态不能为空!
//    MSG_601106,//601106^auditBy should not be empty!^审批人不能为空!
//    MSG_601101,//601101^MessageId  should not be empty!^消息ID不能为空!
//    MSG_601102,//601102^MessageFlag  should not be empty!^消息状态不能为空!
//    MSG_601107,//601003^MessageType  should not be empty!^消息分类不能为空!
//    MSG_601110,//601005^MessageContent  should not be empty!^消息内容不能为空!
//    MSG_601108,//601008^Title  should not be empty!^消息标题不能为空!
//    MSG_601006,//601006^Message Catalog  should not be empty!^消息类型不能为空!
//    MSG_601009,//601009^Message Condition Values  should not be empty!^消息条件值不能为空!
//    MSG_510016,//510^userId should not be empty^userId不能为空!
//    MSG_366607,//366607^operation type should not be empty!^操作类型不能为空!
//    MSG_309507,//309507^Content should not be null^申请内容不能为空
//    MSG_601005,//601005^Title  should not be empty!^消息标题不能为空!
//    MSG_600224,//600224^Old Login Name can not be null^旧登录名不能为空
//    MSG_600225,//600225^New Login Name can not be null^新登录名不能为空
//    MSG_700854,//700854^New Credit Amount should not be empty!^新额度不能为空
//    MSG_700856,//700856^Current Credit Amount should not be empty!^当前额度不能为空！
//    MSG_700857,//700857^Transaction Code should not be empty!^transcode不能为空
//    MSG_700858,//700858^Game Amount should not be empty!^游戏额度不能为空！
//    MSG_700859,//700859^Current Game Credit Amount should not be empty!^当前游戏额度不能为空！
//    MSG_700860,//700860^New Game Credit Amount should not be empty!^新游戏额度不能为空！
//    MSG_700863,//700863^New Promotion Credit Amount should not be empty!^新优惠额度不能为空！
//    MSG_999015,//999014^callMode  should not be empty!^拨打方式不能为空!
//    MSG_308576,//308577^Withdrawal conversion rate cannot be empty!^取款转换汇率不能为空!
//    MSG_308574,//308579^Withdrawal conversion amount cannot be empty!^取款转换金额不能为空!
//    MSG_910015,//910015^commentContent should not be empty!^问答评论不能为空!
//    MSG_910008,//910008^questionId should not be empty!^问答ID不能为空!
//    MSG_910009,//910009^questionId should not be empty!^问答ID不能为空!
//    MSG_910016,//910016^flag should not be empty!^评论状态不能为空!
//    MSG_380105,//380105^Login Flag shouldn't null!^激活状态不允许为空！
//    MSG_380112,//380112^bondType  shouldn't null!^绑定类型不允许为空！
//    MSG_400125,//400125^Invoke Method should not be null^调用方法不能为空
//    MSG_400126,//400126^Sql Content should not be null^SQL内容不能为空
//    MSG_400130,//400130^Sql Content MD5 should not be null^SQL MD5内容不能为空
//    MSG_400127,//400127^Period should not be null^执行时长不能为空
//    MSG_400128,//400128^Execute Start Time should not be null!^ 执行开始时间不能为空
//    MSG_400129,//400129^Execute End Time should not be nulls!^ 执行结束时间不能为空
//    MSG_400140,//400140^Invoke Method should not be null^调用方法不能为空
//    MSG_305013,//305013^Task No cannot be null!^任务编号不能为空!
//    MSG_305010,//305010^EexSql should not be empty!^执行SQL不能为空!
//    MSG_910001,//910001^type should not be empty!^类型不能为空!
//    MSG_305009,//305009^Send type should not be empty!^发送类型不能为空!
//    MSG_305011,//305011^Property1 cannot be null!^属性1不能为空!
//    MSG_305012,//305012^Property2 cannot be null!^属性2人不能为空!
//    MSG_334926,//334926^Task No should not be empty!^流水编号不能为空!
//    MSG_334955,//334955^Reference Id should not be empty!^关联单号不能为空!
//    MSG_503100,//503101^Constant ID  should not be empty,and length must for 2  to 20 between!^常量ID不能为空,长度必须在2和20之间
//    MSG_201501,//201501^GameKind should not be empty,and length must for 1 to 3 between!^游戏Kind不能为空,长度必须在 1和3 之间
//    MSG_208007,//208007^IP should not be empty,and length must for 1 to 63 between!^IP不能为空,长度必须在 1和63 之间
//    MSG_503099,//503102^ProductId length must for 2 to 3 between!^产品Id 长度必须在2和3之间!
//    MSG_503405,//503405^SendContent should not be empty,and length must for 1 to {0} between!^发送内容不能为空,长度必须在1和{0}之间
//    MSG_201210,//201210^BankName should not be empty,and length must for 2 to 40 between!^银行名不能为空,长度必须在2和40之间!
//    MSG_100015,//100015^LastUpdateBy should not be empty,and length must for 2 to 20 between!^LastUpdateBy不能为空,长度必须在2和20之间!
//    MSG_300543,//300543^RatePoint should not be empty,and length must for 2 to 50 between!^比特币查询UUID不能为空,长度必须在 2和 50之间
//    MSG_503408,//503408^MailTitle should not be empty,and length must for 1 to 200 between!^邮件标题不能为空,长度必须在1和200之间
//    MSG_503502,//503502^RequestID should not be empty,and length must for 2 to 26 between!^批量发送短信记录ID不能为空,长度必须在 2和 26之间
//    MSG_503409,//503409^CreatedBy should not be empty,and length 2 to 15 between!^创建人不能为空,长度必须在 2和15 之间
//    MSG_300499,//300503^BankAccountName should not be empty,and length must for 2 to 40 between!^BankAccountName不能为空,长度必须在2和40之间!
//    MSG_300508,//300508^BTC Serialize Number should not be empty,and length must for 6 to 500 between!^比特币密串不能为空,长度必须在 6和 500之间
//    MSG_201701,//201730^Length of RealName must between {0} and {1}^真实姓名长度必须在{0}到{1}之间
//    MSG_201699,//201731^Length of Phone must between 8 and 16^会员电话长度必须在8到16之间
//    MSG_309002,//309002^ApproveBy length must for 2 to 20 between!^审批人长度必须在2和20之间
//    MSG_300532,//300532^RemittanceAccountNo {0} should not be empty,and length must for 6 to 200 between!^存款人{0}地址不能为空,长度必须在 6和 200之间
//    MSG_300406,//300406^DepositBy  should not be empty,and length must for 1 to 200 between!^存款人不能为空,长度必须在1和200之间!
//    MSG_300407,//300407^DepositType  should not be empty,and length must for 1 to 20 between!^存款方式不能为空,长度必须在1和20之间!
//    MSG_300403,//300404^DepositLocation  should not be empty,and length must for 1 to 50 between!^DepositLocation不能为空,长度必须在1和50之间!
//    MSG_300409,//300409^BankName  should not be empty,and length must for 2 to 40 between!^银行名不能为空,长度必须在2和40之间!
//    MSG_300410,//300410^BankCity  should not be empty,and length must for 2 to 50 between!^银行所在城市不能为空,长度必须在2和50之间!
//    MSG_300411,//300411^BranchName  should not be empty,and length must for 2 to 50 between!^支行名不能为空,长度必须在2和50之间!
//    MSG_305859,//305858^The login account cannot be empty, the length must be between 5 and 8^登录账户必须在5到8之间
//    MSG_509305,//509305^AccountName should not be empty,and length must for 2 to 20 between!^推荐人账号不能为空,长度必须在 2和 20之间
//    MSG_509309,//509309^RealName should not be empty,and length must for 2 to 20 between!^推荐人真实姓名不能为空,长度必须在 2和 20之间
//    MSG_509311,//509311^AccountNameBy should not be empty,and length must for 2 to 20 between!^被推荐人账号不能为空,长度必须在 2和 20之间
//    MSG_301608,//301608^Billno should not be empty,and length must between 10 and 30!^在线支付单号不能为空,长度必须在10和30之间!
//    MSG_509315,//509315^RealNameBy should not be empty,and length must for 2 to 20 between!^被推荐人真实姓名不能为空,长度必须在 2和 20之间
//    MSG_509317,//509318^PhoneBy should not be empty,and length must for 8 to 16 between!^会员电话不能为空,长度必须在 8和 16之间
//    MSG_309311,//309311^Content should not be empty,and length must for 1 to 200 between!^申请内容不能为空,长度必须在 1和 200之间
//    MSG_305844,//305847^LoginName should not be empty,and length must between 4 to 20.^登录名不能为空,长度必须在4和20之间.
//    MSG_829007,//829007^PromotionType should not be empty,and length must for 2 to 20 between!^优惠类型不能为空,长度必须在 2和 20之间
//    MSG_201703,//201709^Email/Phone should not be empty,and length must for {0} to {1} between!^邮箱/电话不能为空,长度必须在{0}和 {1}之间
//    MSG_305857,//305858^LoginName should not be empty,and length must between 4 to 15.^登录名不能为空,长度必须在4和15之间.
//    MSG_306109,//306109^RateName should not be empty,and length must for 1 to 20 between!^洗码名不能为空,长度必须在 1和 20之间
//    MSG_306114,//306116^XmType should not be empty,and length must for 1 to 20 between!^洗码类型不能为空,长度必须在 1和 20之间
//    MSG_201697,//201714^ParentLoginName should not be empty,and length must between 2 to 20.^上线会员不能为空,长度必须在2和20之间.
//    MSG_201737,//201737^Length of id card number must between 8 and 30^证件号码长度必须在8到30之间
//    MSG_201749,//201749^Answer1 should not be empty,and length must between 1 to 10.^答案1不能为空,长度必须在1和10之间.
//    MSG_201751,//201751^Answer2 should not be empty,and length must between 1 to 10.^答案2不能为空,长度必须在1和10之间.
//    MSG_201752,//201752^gameKey should not be empty,and length must between 6 to 15.^游戏密码不能为空,长度必须在6和15之间.
//    MSG_306110,//306110^UpdatedBy should not be empty,and length must for 2 to 20 between!^修改人不能为空,长度必须在 2和 20之间
//    MSG_205402,//205402^length of ID must between 10 and 20^ID长度必须在10到20之间
//    MSG_205404,//205404^AssignedBy should not be empty,and length must for 2 to 20 between!^处理人不能为空,长度必须在 2和 20之间
//    MSG_202019,//202019^CustomerId should not be empty,and length must for 2  to 15 between!^用戶ID不能为空,长度必须在2和15之间
//    MSG_202020,//202020^FingerPrintKey should not be empty,and length must for 2  to 15 between!^指纹ID不能为空,长度必须在2和15之间
//    MSG_202021,//202021^DeviceType should not be empty,and length must for 1  to 2 between!^设备类型不能为空,长度必须在1和2之间
//    MSG_202004,//202005^LastLoginIp should not be empty,and length must for 1 to 63 between!^最后登录IP不能为空，长度必须在 1 和 63 之间！
//    MSG_202147,//202147^VerifyCode length must for 1 to 16 between!^会员预留信息 长度必须在1和16之间!
//    MSG_209002,//209002^LoginName should not be empty,and length must between 4 to 20.^登录名不能为空,长度必须在4和20之间.
//    MSG_202013,//202013^RealName should not be empty,and length must for 2 to 60 between!^真实姓名不能为空,长度必须在 2和 60之间
//    MSG_202661,//202661^CustomerId should not be empty,and length must for 2 to 20 between!^客户ID不能为空,长度必须在 2和 20之间！
//    MSG_202152,//202160^PTKey should not be empty,and length must for 6 to 16 between!^PT游戏密码不能为空,长度必须在 6和 16之间！
//    MSG_202151,//202161^ApKey should not be empty,and length must for 8 to 12 between!^德州扑克密码不能为空,长度必须在 8和 12之间！
//    MSG_202224,//202224^Old ApKey should not be empty,and length must for 8 to 12 between!^旧德州扑克密码不能为空,长度必须在 8和 12之间！
//    MSG_202231,//202231^Old Pwd should not be empty,and length must for 1 to 39 between!^旧密码不能为空,长度必须在 2和 20之间！
//    MSG_202108,//202108^IpAddress should not be empty,and length must for 2 to 20 between!^IP地址不能为空,长度必须在 2和 20之间！
//    MSG_202662,//202662^ProductId should not be empty,and length must for 2 to 20 between!^产品ID不能为空,长度必须在 2和 20之间！
//    MSG_202660,//202663^FacebookId should not be empty,and length must for 2 to 20 between!^FacebookId不能为空,长度必须在 2和 20之间！
//    MSG_202664,//202664^DeviceId should not be empty,and length must for 2 to 50 between!^设备ID不能为空,长度必须在 2和 50之间！
//    MSG_300808,//300808^BankAccountName should not be empty,and length must for {0} to {1} between!^银行开户名不能为空,长度必须在 {0} 和 {1}之间
//    MSG_300811,//300811^BankAccountType should not be empty,and length must for 2 to 40 between!^银行账号类型不能为空,长度必须在 2和 40之间
//    MSG_300812,//300812^BankCity should not be empty,and length must for 2 to 40 between!^银行所在城市不能为空,长度必须在 2和 40之间
//    MSG_300813,//300813^BankName should not be empty,and length must for 2 to 100 between!^银行名不能为空,长度必须在 2和 100之间
//    MSG_300814,//300814^BranchName should not be empty,and length must for 2 to 50 between!^开户网点不能为空,长度必须在 2和 50之间
//    MSG_300815,//300815^BankCountry should not be empty,and length must for 2 to 50 between!^银行所在国家不能为空,长度必须在 2和 50之间
//    MSG_202190,//202190^CustomerId should not be empty,and length must for 2 to 30 between!^会员ID不能为空,长度必须在 2和 30之间！
//    MSG_206602,//206603^IpAddress should not be empty,and length must for 2 to 63 between!^最后登录IP不能为空,长度必须在 2和 63之间！
//    MSG_201212,//201212^BankCountry should not be empty,and length must for 2 to 30 between!^银行所在国家不能为空,长度必须在 2和 30之间
//    MSG_301110,//301112^LevelStatus should not be empty,and length must for 1 to 3 between!^会员等级不能为空,长度必须在 1和 3之间
//    MSG_309602,//309602^AssignedTo should not be empty,and length must for 2 to 20 between!^指定处理人不能为空,长度必须在2和20之间！
//    MSG_309607,//309607^CallType should not be empty,and length must for 1 to 10 between!^通话类型不能为空,长度必须在1和10之间！
//    MSG_309701,//309701^id should not be empty,and length must for 2 to 20 between!^id 不能为空,长度必须在 2 和 20 之间
//    MSG_309806,//309806^InvalidReasons  should not be empty,and length must for 1 to 20 !^无效原因不能为空,长度必须在 1 和 20 之间
//    MSG_309810,//309810^remarks  should not be empty,and length must for 1 to 100 !^处理意见不能为空,长度必须在 1 和 100 之间
//    MSG_304912,//304914^xmType should not be empty,and length must for 1~10^洗码类型不能为空,长度必须在1到10之间!
//    MSG_304909,//304918^EndPointType should not be empty,and length must for 1~200^提交端类型不能为空,长度必须在1到200之间!
//    MSG_304908,//304919^EndPointUrl should not be empty,and length must for 1~50^提交端URL不能为空,长度必须在1到50之间!
//    MSG_305130,//305130^Length of Target Login Name must between 2 and 20!^转入账号登录名长度必须在2到20之间!
//    MSG_305203,//305203^BankAccountNo should not be empty,and length must for 2 to 500 between!^银行账号不能为空,长度必须在 2和 500之间
//    MSG_305204,//305204^BankAccountType should not be empty,and length must for 2 to 20 between!^银行账号类型不能为空,长度必须在 2和 20之间
//    MSG_305206,//305206^BankCity should not be empty,and length must for 2 to 60 between!^银行所在城市不能为空,长度必须在 2和 60之间
//    MSG_304804,//304804^BankAccountNo should not be empty,and length must between 2 and 300!^银行账号长度必须在2到300之间!
//    MSG_304803,//304804^BankAccountNo should not be empty,and length must between 2 and 40!^银行账号长度必须在2到40之间!
//    MSG_304806,//304806^BankProvince should not be empty,and length must between 2 and 20!^省份长度必须在2到20之间!
//    MSG_304805,//304806^BankName should not be empty,and length must between 2 and 20!^银行名长度必须在2到20之间!
//    MSG_304836,//304836^BankId should not be empty,and length must between 2 and 50!^银行开户id长度必须在2到50之间!
//    MSG_304802,//304803^RatePoint should not be empty,and length must between 2 and 50!^比特币查询UUID长度必须在2到50之间!
//    MSG_211603,//211603^referenceId  should not be empty,and length must for 2 to 25 between!^referenceId不能为空,长度必须在2和25之间!
//    MSG_300003,//300004^CustomerId should not be empty,and length must for 2 to 22 between!^CustomerId不能为空,长度必须在2和22之间!
//    MSG_300002,//300002^DepositurationId should not be empty,and length must for 2 to 22 between!^DepositurationId不能为空,长度必须在2和22之间!
//    MSG_300000,//300003^ReferenceId should not be empty,and length must for 2 to 32 between!^ReferenceId不能为空,长度必须在2和32之间!
//    MSG_300008,//300013^BankAccountName should not be empty,and length must for 2 to 20 between!^银行开户名不能为空,长度必须在2和20之间!
//    MSG_300007,//300008^DepositLocation should not be empty,and length must for 1 to 50 between!^存款地点不能为空,长度必须在1和50之间!
//    MSG_300036,//300011^DepositType should not be empty,and length must for 1 to 20 between!^ 存款方式不能为空,长度必须在1和20之间!
//    MSG_300645,//300645^description should not empty and length must for 1 to 2000!^需求描述不能为空 长度只能1到2000之间
//    MSG_300646,//300646^nickname length must for 1 to 20 between!^昵称长度只能1到20之间
//    MSG_300648,//300648^QQ/Weibo length must for 1 to 20 between!^qq/微博账号  长度只能1到20之间
//    MSG_308903,//308903^RecommendPhone should not be empty,and length must for 8 to 16 between!^推荐会员电话不能为空,长度必须在 8和 16之间
//    MSG_202022,//202022^IP should not be empty,and length must for 2  to 63 between!^IP不能为空,长度必须在2和63之间
//    MSG_503501,//503502^AnnouncementId should not be empty,and length must for 1 to 20 between!^公告ID不能为空,长度必须在 1和 20之间
//    MSG_504602,//504602^Content should not be empty,and length must for 1 to 400 between!^公告内容不能为空,长度必须在 1和 400之间
//    MSG_504612,//504612^PhotoName length must for 1 to 50 between!^图片名称 长度必须在1和50之间!
//    MSG_504613,//504613^PhotoPath length must for 1 to 50 between!^图片路径 长度必须在1和50之间!
//    MSG_505909,//505909^Execute delete effect 0 row!^执行删除影响行数为0！
//    MSG_300303,//300303^PromotionName length must for 2 to 4000 between!^模版内容 长度必须在2和4000之间!
//    MSG_204662,//204662^effectiveMonth should not be empty,and length must for 1 to 2 between!^生效月份,长度必须在 1和 2之间！
//    MSG_202659,//202663^effectiveYear should not be empty,and length must be 4 length!^生效年份,长度必须为4位！
//    MSG_506500,//506503^Content length must for 1 to 1000 between!^内容 长度必须在1和1000之间!
//    MSG_506499,//506502^Subject length must for 1 to 150 between!^标题 长度必须在1和150之间!
//    MSG_403407,//403407^GameId should not be empty,and length must for 2 to 20 between!^游戏ID不能为空,长度必须在 2和 20之间
//    MSG_403307,//403307^Value length must for 1 to 200 between!^常量值长度必须在1和1000之间
//    MSG_403302,//403302^Key should not be empty,and length must for 1 to 30 between!^KEY不能为空,长度必须在1和30之间
//    MSG_403405,//403407^Value length must for 1 to 200 between!^常量值 长度必须在1和1000之间!
//    MSG_402942,//402942^GameId should not be empty,and length must for 3 to 10 between!^游戏ID不能为空,长度必须在 3和 10之间
//    MSG_402943,//402943^ProductId should not be empty,and length must for 3 to 4 between!^产品ID不能为空,长度必须在 3和 4之间
//    MSG_402903,//402903^GameName should not be empty,and length must for 3 to 10 between!^游戏名称不能为空,长度必须在 3和 10之间
//    MSG_402106,//402106^ProductDesc length is not greater than 50!^产品描述长度不能大于50!
//    MSG_300098,//300101^BankCode should not be empty,and length must for 2 to 30 between!^银行编码 不能为空,长度必须在 2 和 30 之间！
//    MSG_300097,//300102^BankAccountName should not be empty,and length must for 2 to 60 between!^银行开户名 不能为空,长度必须在 2 和 60 之间！
//    MSG_300095,//300112^CustomerLevel should not be empty,and length must for 1 to 200 between!^会员级别 不能为空,长度必须在 1 和 200 之间！
//    MSG_300093,//300113^TrustLevel should not be empty,and length must for 1 to 200 between!^存款优先级别 不能为空,长度必须在 1 和 200 之间！
//    MSG_300092,//300108^BankName should not be empty,and length must for 2 to 20 between!^银行名 不能为空,长度必须在 2 和 20 之间！
//    MSG_300091,//300109^Province should not be empty,and length must for 2 to 50 between!^省份 不能为空,长度必须在 2 和 50 之间！
//    MSG_300090,//300110^BankCity should not be empty,and length must for 2 to 50 between!^银行所在城市 不能为空,长度必须在 2 和 50 之间！
//    MSG_305706,//305706^TrustLevel should not be empty,and length must for 1 to 40 between!^存款优先级别 不能为空,长度必须在 1 和 40 之间！
//    MSG_301157,//301157^ApproveBy should not be empty,and length must for 2 to 20 between!^审批人不能为空,长度必须在 2 和20 之间！
//    MSG_300202,//300202^PromotionType should not be empty,and length must for 2 to 15 between!^优惠类型不能为空,长度必须在 2和 15之间
//    MSG_300203,//300203^PromotionName should not be empty,and length must for 2 to 30 between!^优惠名称不能为空,长度必须在 2和 30之间
//    MSG_400607,//400607^LastUpdatedBy length must for 1 to 20 between!^最后修改人长度必须在1和20之间
//    MSG_400102,//400102^RoleName should not be empty,and length must for 3 to 10 between!^角色名不能为空,长度必须在 3 和 10 之间
//    MSG_400104,//400104^RoleDesc should not be empty,and length must for 2 to 30 between!^角色描述不能为空,长度必须在 2 和 30 之间
//    MSG_400108,//400108^ParentRoleId should not be empty,and length must for 6 to 10 between!^父角色ID不能为空,长度必须在 6和 10之间
//    MSG_400402,//400402^roleID should not be empty,and length must for 1 to 10 between!^角色ID不能为空,长度必须在 6和 10之间
//    MSG_400407,//400407^FunctionId should not be empty,and length must for 1 to 22 between!^功能菜单ID不能为空,长度必须在 6和 22之间
//    MSG_500186,//500186^MinBetAmount must be a positive integer, the length is more than 9!^最小有效投注额必须是一个正整数,长度不超过9!
//    MSG_500187,//500187^MaxBetAmount must be a positive integer, the length is more than 9!^最大有效投注额必须是一个正整数,长度不超过9!
//    MSG_500188,//500188^Only one of the major categories can be configured at the same time.!^大类洗码配置小类洗码配置只能同时存在一个!
//    MSG_500189,//500189^CustomerLevel or BetAmount must config one.^会员星级和投注额必须配置一个!
//    MSG_500190,//500190^CustomerLevel or BetAmount choose one!^会员星级和投注额只能选一!
//    MSG_500191,//500191^XM Config exist other Type Config!^星级洗码配置存在其他的投注配置!
//    MSG_500192,//500192^XM Config exist other Type Config!^投注洗码配置存在其他的星级配置!
//    MSG_500193,//500193^Max Bet Amount should bigger than Min Bet Amount!^最大投注额度不可以小于最小投注额度!
//    MSG_500194,//500194^XM config effect start time should not be null!^洗码生效开始时间不可以为空!
//    MSG_500195,//500195^XM config effect start time should be xm start date^洗码生效开始时间应该为洗码周期开始日期!
//    MSG_500196,//500196^XM config effect end time should not earlier than effect start time!^洗码生效结束时间不可以早于开始时间!
//    MSG_500197,//500197^XM config effect end time should be xm end date^洗码生效结束时间应该为洗码周期结束日期!
//    MSG_500198,//500198^XM Config can't repeat effect time range!^洗码配置生效时间段不能重复!
//    MSG_500199,//500199^XM Config can't repeat effect time range!^存在相同星级的洗码配置!
//    MSG_500200,//500200^XM Config can't repeat effect time range!^存在相同投注额度区间的洗码配置!
//    MSG_500202,//500202^id not exist!^洗码配置ID不存在!
//    MSG_500203,//500203^Customer ID does not exists in T_CUSTOMER_OPERATION!^客户没有洗码权限!
//    MSG_500204,//500204^Same XM type has repeat submit within 120 seconds!^短时间内洗码重复提交!
//    MSG_501213,//500213^total amount not equals list amount.^总洗码金额和分类和不相等!
//    MSG_501214,//500214^total bettingAmount not equals list amount.^总要求投注额不相等!
//    MSG_501215,//500215^xm Amount is limited ,must be greater than min limit rebate amount!^当前洗码金额已被限制,值必须大于最低洗码金额限制!
//    MSG_500216,//500216^XMdate must be week {0} to week {1} !^洗码周期必须为周 {0} 到周 {1}
//    MSG_500217,//500217^XMdate must from {0} to {1}!^洗码周期时间必须在必须为：{0} 到 {1}
//    MSG_500218,//500218^Amount not correct format,must be **.00!^{0}洗码金额不正确,格式必须为**.00
//    MSG_500219,//500219^Query wallet reduceBetAmount exception!^查询新钱包优惠流水出现异常!
//    MSG_501239,//500220^xmType not correct format,must be XM**!^洗码类型格式不正确,格式必须为XM**
//    MSG_501238,//500221^BettingAmount not correct format,must be **.00!^有效投注额格式不正确,格式必须为**.00
//    MSG_501237,//500222^RATE not correct format,must be **.00!^洗码佣金比率格式不正确,格式必须为**.00
//    MSG_500223,//500223^Amount not correct format,must be **.00!^金额格式不正确,格式必须为**.00
//    MSG_501236,//500224^StartDate must be end of '00:00:00'!^洗码开始时间必须是'00:00:00'结束
//    MSG_500225,//500225^EndDate must be end of '23:59:59'!^洗码结束时间必须是'23:59:59'结束
//    MSG_500226,//500226^RATE not correct format,must be **.00!^洗码比格式不正确,格式必须为**.00
//    MSG_500227,//500227^CreatedBy not exist!^创建人不存在！
//    MSG_500228,//500228^Query wallet reduceBetAmount exception!^查询新钱包优惠流水出现异常!
//    MSG_500229,//500229^clean show pwd and return back verifyCode info error!^清空显示密码,还原预留信息出现错误！
//    MSG_500230,//500230^Customer ID does not exists in T_CUSTOMER_OPERATION. Please check trigger TRIG_CUST_OPER^用户id在T_CUSTOMER_OPERATION表中不存在！请检查触发器TRIG_CUST_OPER！
//    MSG_500231,//500231^Remarks should not be empty,and length must for 1  to 200 between!^备注不能为空,长度必须在1和200之间！
//    MSG_500232,//500232^Token validate fail!^令牌验证失败
//    MSG_500233,//500233^user not exist!^用户不存在!
//    MSG_500234,//500234^TransCode not right,must be [113504,113506,113507,113508] one of!^不正确的值,值只能为[113504,113506,113507,113508] 其中之一
//    MSG_500235,//500235^TransCode and CreditMode not match!^交易码与额度模式不匹配!
//    MSG_500236,//500236^trans's amount can not greater than customer's  credit!^转账金额不能超过会员现金额度!
//    MSG_500237,//500237^TransCode and CreditMode not match !^交易码与额度模式不匹配
//    MSG_500238,//500238^total count is not number!^总数不是数字
//    MSG_500239,//500239^This request was already done,can not do again!^该请求已经完成，无法重复发起
//    MSG_500240,//500240^CreatedBy not exist!^操作人不存在!
//    MSG_500241,//500241^Amount must less or equals than customer credit!^扣除额度必须小于或等于会员当前额度!
//    MSG_500242,//500242^Amount must bigger than zero!^扣除额度必须大于0!
//    MSG_500243,//500243^If no input LoginName(customerID), criterion must input CreatedDate and TransCode^如果不输入账户名,则一定要输入时间段和额度类型
//    MSG_500244,//500244^Status not correct format,must be number^Flag标识格式不正确，必须为number
//    MSG_500245,//500245^Login Name and Customer Id should not null^登入名及用戶id不能为空值
//    MSG_500246,//500246^TransCode not correct format^提交类型格式不正确
//    MSG_500247,//500247^EndPoint not correct format^提交来源格式不正确
//    MSG_500248,//500248^Status not correct format^查询状态格式不正确
//    MSG_500249,//500249^Start date must less than end date!^开始时间必须小于结束时间！
//    MSG_500250,//500250^Week Commission of the period not correctly!^周佣金周期起止日期不正确！
//    MSG_500251,//500251^{0} miss product constants {1}!^ {0}缺少产品常量配置项 {1}
//    MSG_500252,//500252^Missing product constants {0} !^缺少产品常量配置项 {0}
//    MSG_500253,//500253^list is null or size is 0!^创建客户投注记录验证失败，原因为：传入的集合为NULL或大小为0
//    MSG_500254,//500254^ID does not existed^ID不存在
//    MSG_500255,//500255^TeamName can't repeat!^球队名不能重复!
//    MSG_500256,//500256^LoginName or customer id  should not be empty!^登录名和用户id不能为同时空!
//    MSG_500257,//500257^The IP is forbidden to register^该ip禁止注册
//    MSG_500258,//500258^Account already exists^账号已存在
//    MSG_500259,//500259^Phone Bond Times Beyond Limit^电话绑定次数超出限制
//    MSG_500260,//500260^Email already bind another account^邮箱已绑定其他账号
//    MSG_500261,//500261^Language format error!^语言格式错误！
//    MSG_500262,//500262^CustomerSource can't repeat!^客户渠道不能重复！
//    MSG_500263,//500263^UpdatedBy does not existed^更新人不存在
//    MSG_500264,//500264^AssignedBy does not existed^处理人不存在
//    MSG_500265,//500265^Invalid Password!^用户名或密码错误！
//    MSG_500266,//500266^Login name was locked already, please contact customer service or waiting {0}:{1} try again!^账号或密码输入多次错误，请确认您的账号密码并等待{0}:{1}后重新输入!
//    MSG_500267,//500267^the Password wrong^密码错误!
//    MSG_500268,//500268^he game account has been disabled, please contact customer service!^游戏帐户已被禁用，请联系客服!
//    MSG_500269,//500269^the Password expired^密码过期!
//    MSG_500270,//500270^Maximum number of login times within 24 hours!^超过24小时内最大登录次数限制!
//    MSG_500271,//500271^Login name was locked already, please contact customer service or waiting 5 minutes try again!^会员账号已被锁住，请五分钟后再试或联系客服进行解锁!
//    MSG_500272,//500272^The game account has been disabled, please contact customer service!^游戏帐户已被禁用，请联系客服!
//    MSG_500273,//500273^Token Type should not empty^Token 类型不能为空
//    MSG_500274,//500274^Email not correct format,must be **@***.***^会员邮箱格式不正确,必须为**@***.***
//    MSG_500275,//500275^Email can be empty,if not empty,and length must for 6 to 50 between!^邮箱可以为空,如果不为空,长度必须在 6和 50之间
//    MSG_500276,//500276^Label Value can not same with old value^修改后的标签值不能与修改前的标签值相同
//    MSG_500277,//500277^Address can be empty,if not empty,and length must for 0 to 50 between!^会员地址可以为空,如果不为空,长度必须在 0和 50之间
//    MSG_500278,//500278^Sub-account cannot add modified withdrawal password!^子账号不能新增修改取款密码!
//    MSG_500279,//500279^Invalid password status^密码状态锁定!
//    MSG_500280,//500280^Invalid password status^密码状态重置!
//    MSG_500281,//500281^LoginName or customer id  should not be empty!^登录名和用户id不能为同时空!
//    MSG_500282,//500282^The original password is incorrect, please enter again!^原密码输入有误,请重新输入！
//    MSG_500283,//500283^Withdrawal password should not be empty,and length must for 6 !^取款密码不能为空,长度必须为6位！
//    MSG_500284,//500284^Modify account must main currency account!^修改的账号必须为本站主币种账号！
//    MSG_500285,//500285^During login by Ap key, check ap effective date, error format exception!^会员用apKey登录，校验时间，时间格式转换失败！
//    MSG_500286,//500286^Invalid password effective date!^德州扑克密码已过期！
//    MSG_500287,//500287^During login by Ap key, encrypt exception!^会员用apKey登录，加密密码失败！
//    MSG_500288,//500288^ApKey error!^德州扑克密码错误！
//    MSG_500289,//500289^faceBook customer info not exist!^Facebook会员信息不存在！
//    MSG_500290,//500290^weChat customer info not exist!^微信会员信息不存在!
//    MSG_500291,//500291^Already modified bank,can't modify!^已生成银改提案不能修改！
//    MSG_500292,//500292^Already come into game lobby,can't modify!^进入过游戏厅不能修改！
//    MSG_500293,//500293^LoginName and pwd Don't match!^登录名,密码不匹配
//    MSG_500294,//500294^YebFlag Must is 0 or 1, and didn't same as old yebFlag!^修改Yeb状态值必须是0或者1，并且不能与原状态一样！
//    MSG_500295,//500295^The official account name don't modify!^非临时登陆名不能修改！
//    MSG_500296,//500296^Register date more more than 7 days,can't modify!^注册时间超过7天不能修改！
//    MSG_500297,//500297^Already producted credit logs,can't modify!^已生成额度记录不能修改！
//    MSG_500298,//500298^Already deposited amount,can't modify!^已生成存款记录不能修改！
//    MSG_500299,//500299^RedEnvelopeStatus Must is 0 or 1, and didn't same as old RedEnvelopeFlag!^修改RedEnvelope状态值必须是0或者1，并且不能与原状态一样！
//    MSG_500300,//500300^During modify Ap key, encrypt exception!^修改会员apKey，加密密码失败！
//    MSG_500301,//500301^During reset Ap key, encrypt exception!^重置会员apKey，加密密码失败！
//    MSG_500302,//500302^Bank Account Length Error!^银行卡错误！
//    MSG_504303,//500303^the customer id shouldn't be empty!^会员ID不能为空！
//    MSG_500304,//500304^During setup Ap key, encrypt exception!^设置会员apKey，加密密码失败！
//    MSG_500305,//500305^Verify Code should not be empty!^验证码不能为空!
//    MSG_504306,//500306^Sub Login Name Repeat!^子账号登录名重复!
//    MSG_504307,//500307^Login Name Repeat!^登录名重复！
//    MSG_504308,//500308^exchangeStatus Must is 0 or 1, and didn't same as old exchangeStatus!^修改exchangeStatus状态值必须是0或者1，并且不能与原状态一样！
//    MSG_504309,//500309^siteId Must in 1-99!^子网站代号必须在1-99！
//    MSG_504310,//500310^loginName not exist or is disable!^会员不存在或不是正式会员！不存在或已失效
//    MSG_504311,//500311^The customer {0} cannot exceed the limit！^客户  {0} 专属链接数量不能超过限制!
//    MSG_504312,//500312^the customer already has this link! ^该客户已存在此专属链接!
//    MSG_504313,//500313^Line Config Record not Exsits!^专属专线配置不存在！
//    MSG_504314,//500314^loginName not exist or is disable!^会员不存在或不符合条件！不存在或已失效!
//    MSG_504315,//500315^The customer {0} has configured in other place！^客户  {0} 已存在配置!
//    MSG_504316,//500316^userName not exist or is disable!^客服不存在！不存在或已失效!
//    MSG_504317,//500317^The user {0} has configured in other place！^客服 {0} 已存在配置!
//    MSG_504318,//500318^ids size must less than 1000!^id个数必须小于1000
//    MSG_504319,//500319^LoginNameList or CustomerLevelList or DepositLevelList or PriorityLevelList should not be empty^四个条件不能为空
//    MSG_504320,//500320^Typecode  not right,must be one of [60013,60014,60015,60016,60017,60018,60020,60021,60022,60023,60024,60025,60026,60027,60028,60007,60004]!^短信类型值只能为[60013,60014,60015,60016,60017,60018,60020,60021,60022,60023,60024,60025,60026,60027,60028,60007,60004]其中之一
//    MSG_504321,//500321^Typecode  not right,must be one of [20013,20014,20015,20016,20017,20018]!^邮件类型值只能为[20013,20014,20015,20016,20017,20018]其中之一
//    MSG_504322,//500322^CreatedDate query range limit:90days!^创建时间查询范围限定为:90天!
//    MSG_504323,//500323^taskNo already exist!^该笔订单号数据已存在
//    MSG_504324,//500324^taskNo cannot be empty^订单编号不能为空值
//    MSG_504325,//500325^potentialVipCustomerId cannot be empty^客户名单id不能为空值
//    MSG_504326,//500326^potentialVipCustomer is not exist^潜力vip客户 id 不存在
//    MSG_504327,//500327^taskNo is not exist^订单编号不存在
//    MSG_504328,//500328^potentialVipCustomer is not exist^沉默激活客户 id 不存在
//    MSG_504329,//500329^Email not correct format^会员邮箱格式不符
//    MSG_504330,//500330^query promotion amount after last withdrawal failed!^查询最后一笔存款之后的优惠金额发生异常
//    MSG_504331,//500331^ReferenceId should not be empty!^提案编号不能为空!
//    MSG_504332,//500332^query depositRequest fail!^提案查询失败！
//    MSG_504333,//500333^WSCustomers''s have unfinished deposits^会员有未处理的存款提案!
//    MSG_500334,//500334^Bill No already exists!^定单号重复！
//    MSG_500335,//500335^ReferenceId already exist!^提案编号已经存在！
//    MSG_500336,//500336^Get referenceId exception!^获取单号异常！
//    MSG_500337,//500337^Deposit Request Failed To Reject!^存款提案拒绝失败！
//    MSG_500338,//500338^Deposit Request Failed To Approve! Request ID:{0}^存款提案批准失败！
//    MSG_500339,//500339^LastUpdate query range limit:90days!^最后更新时间查询范围限定为:90天!
//    MSG_500341,//500341^When the loginName or requestId is empty the create date range or rebate period date range or audit date must not empty!^登录名或提案ID为空时，创建时间段、审批时间段必须要存在一组值！
//    MSG_500342,//500342^BtcAmount less than point eight and must be greater than zero!^BTC金额最多8位小数！
//    MSG_500343,//500343^Rate of BTC to CNY must be number format's string and greater than 0 and should like **.00!^BTC to CNY 兑换只能是大于0的数字,并且小数位必须在2至8倍！
//    MSG_500344,//500344^Amount must bigger than zero!^转换的金额必须大于0！
//    MSG_500345,//500345^the deposit bank is not exist!^该存款银行账号不存在!
//    MSG_500346,//500346^the deposit bank signature check failed!^该存款银行signature校验失败!
//    MSG_500347,//500347^Submit time from last rejected deposit request within {0} minutes^提案距离会员上笔驳回存款提案小于{0}分钟
//    MSG_500348,//500348^WSCustomers''s front deposit request flag is reject^会员上笔存款提案状态为驳回
//    MSG_500349,//500349^web deposit apply createdBy can only be himself^网站存款提案创建人必须为本人
//    MSG_500350,//500350^Dict Gorup END_POINT_TYPE is empty please set first!^数据字典 END_POINT_TYPE值为空,请先配置!
//    MSG_500351,//500351^Remarks length must for 1 to 200 between!^备注长度必须在1和400之间
//    MSG_500352,//500352^WeChat Account Must not empty and length must 6-200！^微信号长度必须为6-200，并且只能为号码或邮箱或数字字母组合！
//    MSG_500353,//500353^RemittanceAccountNo should not be empty,and length must for 6 to 200 between!^汇款卡号不能为空,长度必须在 6和 200之间!
//    MSG_500355,//500355^LoginName time error!^该登录名发送次数过于频繁，稍后再试!
//    MSG_500356,//500356^LoginName MAX!^已超过24小时内登录名最高发送次数
//    MSG_500357,//500357^IP MAX!^已超过24小时内最高发送次数
//    MSG_500358,//500358^IP time error!^该ip发送次数过于频繁，稍后再试
//    MSG_500359,//500359^Phone and Type MAX!^已超过同一天{0}功能类型最高发送次数
//    MSG_500361,//500361^WSCustomers's have unfinished Modify RealName Request!^会员有未处理的修改真实姓名提案!
//    MSG_500362,//500362^Real name must be modified!^修改的真实姓名不能等原姓名
//    MSG_500363,//500363^WSCustomers's have unfinished Modify Phone Request!^会员有未处理的修改电话提案!
//    MSG_500364,//500364^Phone should not be empty,and length must for 8 to 16 between!^会员电话不能为空,长度必须在 8和 16之间
//    MSG_500365,//500365^Phone can't repeat!^很抱歉,该电话已被注册,请联系客服,谢谢！
//    MSG_500366,//500366^Phone must be modified!^修改的电话不能等于原电话!
//    MSG_500367,//500367^Current status is already binding!^当前状态已经绑定！
//    MSG_500368,//500368^The same accounts contains wrong account!^相同号码绑定账户中含有非该号码绑定的账户！
//    MSG_500369,//500369^Customer Bank doesnot existed!^要删除的银行卡不存在!
//    MSG_500370,//500370^AMTC channel must only have AMTC Bank Account!^来源自AMTC的用户只能有AMTC类型的银行卡!
//    MSG_500371,//500371^Not AMTC channel can't have AMTC Bank Account!^非AMTC的用户不能有AMTC类型的银行卡！
//    MSG_500372,//500372^Bank Card Added to Reach Cap!^{0}会员可添加银行卡己达到上限{1}张！
//    MSG_500373,//500373^Alipay Account Added to Reach Cap!^{0}会员可添加支付宝账号钱包己达到上限{1}张！
//    MSG_500374,//500374^Alipay Qrcode Added to Reach Cap!^{0}会员可添加支付宝二维码钱包己达到上限{1}张！
//    MSG_500375,//500375^Virtual Coin Card Added to Reach Cap!^{0}会员可添加虚拟卡己达到上限{1}张
//    MSG_500376,//500376^CreateAccountBank's PriorityOrder can't repeat!^会员银行帐户或会员取款帐号优先级不能重复!
//    MSG_500377,//500377^CreateAccountBank's PriorityOrder can't repeat!^员比特币帐号不能重复!
//    MSG_500378,//500378^Cannot delete bank card, have unfinish withdrawal request!^暂时无法删除银行卡，存在未完成的取款！
//    MSG_500379,//500379^WSCustomers's have unfinished Modify Bank Request!^会员有未处理的修改银行信息提案!
//    MSG_500380,//500380^Customer Bank type  not match with currency!^银行卡类型catalog和币种不对应！
//    MSG_500381,//500381^CreateAccountBank's PriorityOrder can't repeat!^会员银行优先级不能重复!
//    MSG_500382,//500382^Cannot modify bank card, have unfinish withdrawal request!^暂时无法修改银行卡，存在未完成的取款！
//    MSG_500383,//500383^User bank type must one of {0}!^用户银行卡类型只能是{0}其中之一！
//    MSG_500384,//500384^customer level status must update!^会员等级必须修改!
//    MSG_500385,//500385^WSCustomers's have unfinished same type modify level!^会员有未处理的修改等级提案!
//    MSG_500386,//500386^The customer has bank information, can't repeat added!!^用户银行信息不能重复添加!
//    MSG_500387,//500387^Integral request approve fail!^积分提案通过失败
//    MSG_500388,//500388^Must synchronize main account to sub account!^只能同步主账号信息至子账号！
//    MSG_500389,//500389^WSCustomers's can't receive monthly salary by configuration!^当前会员已配置不能领取月工资!
//    MSG_500390,//500390^promotion apply times more than require.^已达到单人领取次数限制！
//    MSG_500391,//500391^Could not apply promotion, because the promotion is expired!^不在活动时间范围内不能申请优惠!
//    MSG_500392,//500392^Customer not belong the special promotion.^用户不属于该特殊优惠!
//    MSG_500393,//500393^Fist deposit promotion has applied.^会员已经存在首存优惠申请记录!
//    MSG_500394,//500394^WSCustomers's have unfinished same type promotion!^会员有未完成的同类型优惠提案!
//    MSG_500395,//500395^do not satisfy logic and promotion limit.^该用户的并列限制条件(IP、电话、姓名、银行卡)已领取过此优惠!
//    MSG_500396,//500396^The IP:{0} has already applied this promotion!^当前IP已经领过此优惠!
//    MSG_500397,//500397^Flow time must less than current time!param:{0}^优惠流水开始时间必须小于当前系统时间!param:{0}!
//    MSG_500398,//500398^Customer level not in promotion customer level limit config!^星级不在优惠配置星级限制范围内！
//    MSG_500399,//500399^Deposit level not in promotion deposit level limit config!^信用级不在优惠配置信用级限制范围内！
//    MSG_500400,//500400^Priority level not in promotion priority level limit config!^优先级不在优惠配置优先级限制范围内！
//    MSG_501401,//500401^EndPointType not in end point type limit config!^终端类型不在优惠配置终端类型限制范围内！
//    MSG_501402,//500402^The UniqueId already exist in promotion requests!^已存在唯一标识的优惠提案！
//    MSG_501403,//500403^Customer level config not exist!^当前星级优惠配置不存在！
//    MSG_501404,//500404^Apply amount cannot over config maxTimeAmount!^申请的优惠金额不能超出优惠配置单次最大优惠金额！
//    MSG_501405,//500405^Apply times cannot over config maxAllow times!^申请的优惠次数不能超出优惠配置最大享受优惠次数！
//    MSG_501406,//500406^Apply amount cannot over config maxAmount!^申请的优惠金额不能超出优惠配置最大限额！
//    MSG_501407,//500407^The device has been applied the promotion!^该设备已经申请过此类优惠！
//    MSG_501408,//500408^The totalBudget not enough!^总预算不够！
//    MSG_501409,//500409^Automatic approval limit exceeded^自动批准超出次数限制
//    MSG_501410,//500410^Automatic approval of  excess once amount limit^超出自动批准单次金额限制
//    MSG_501411,//500411^The phone has already applied this promotion!^当前手机号已经领过此优惠!
//    MSG_501412,//500412^{0}^{0}
//    MSG_501413,//500413^Consume Promotion failed, create promotion request error!^领取红包失败，生成优惠提案失败！
//    MSG_501414,//500414^flag not right,must be 2,-3 one of!^审批状态不正确的值,值只能为2,-3其中之一
//    MSG_501415,//500415^LastUpdatedBy not exist!^审批人不存在！
//    MSG_501416,//500416^WSCustomers's have unfinished apply!^会员有未处理的申请!
//    MSG_501417,//500417^Real Name decrpty failed!^真实姓名解密失败!
//    MSG_501418,//500418^Real Name decrpty failed!^真实姓名解密失败!
//    MSG_501419,//500419^FirstDepositCredit must be greater than zero,format must be **.00!^被推荐人首存金额必须大于0,格式必须为**.00
//    MSG_501420,//500420^FirstDepositCredit must be greater than zero,format must be **.00!^被推荐人有效投注额必须大于0,格式必须为**.00
//    MSG_501421,//500421^References and referral cannot be recommended for each other!^推荐人与被推荐人不能相互推荐申请
//    MSG_501422,//500422^References and referral currency cannot equals!^推荐人与被推荐人币种必须一致
//    MSG_501423,//500423^AccountNameBy apply recommend promotion only once!^每位会员只能提交一次，如填写信息有误，请联系客服处理
//    MSG_501424,//500424^Approve by not exist!^审核人不存在
//    MSG_501425,//500425^Unable to process Recommend Request!^无法处理好友推荐请求提案
//    MSG_501426,//500426^Get NewRequestId fail!^生成其他提案ID失败！
//    MSG_501427,//500427^WSCustomers's have unfinished Other Request!^会员有未处理的其它类提案!
//    MSG_501428,//500428^Has undo or approve other requests!^该旧代理存在未处理或者已经审批的转三级分销的其他提案
//    MSG_501429,//500429^The agent has undo modify credit requests!^该代理存在未处理修改额度提案!
//    MSG_501430,//500430^The new agent has undo modify credit requests!^新代理存在未处理修改额度提案!
//    MSG_501431,//500431^New agent can not be old agent downline!^新代理不能是旧代理的下线!
//    MSG_501432,//500432^Agent's downline has undo modify parent requests!^该代理的下线存在未处理修改上级提案!
//    MSG_501433,//500433^Customer sub account should not modify email!^子账号不可以修改邮箱!
//    MSG_501434,//500434^WSCustomers's have unfinished Modify Email Request!^会员有未处理的修改邮箱提案!
//    MSG_501435,//500435^flag must in 2,-3!^状态必须在2,-3之中！
//    MSG_501436,//500436^empty ids!^有效id为空！
//    MSG_501437,//500437^too many rows!^删除数据不能超过1000条!
//    MSG_501438,//500438^recCount  not correct format,must be number!^建议帐号数量格式不正确,格式必须为number!
//    MSG_501439,//500439^Error Prefix!^前缀设定错误，请确认前缀常量配置!
//    MSG_501440,//500440^SYS_ACCT_NUM_PC or SYS_ACCT_NUM_MOB set not right please check!^!产品常量SYS_ACCT_NUM_PC或者SYS_ACCT_NUM_MOB配置不正确！
//    MSG_501441,//500441^OverChat!^已超出账号长度规范, 无有效账号可提供!
//    MSG_501442,//500442^generation requestId failed!^生成requestId失败！
//    MSG_501443,//500443^had already create done request balance By this reqeust Id!^已经创建过当前提案ID的额度快照
//    MSG_501444,//500444^Get NewRequestId fail!^生成修改额度提案ID失败！
//    MSG_501445,//500445^WSCustomers's have unfinished Modify Credit Request!^会员有未处理的修改额度提案!
//    MSG_501446,//500446^Credit Type is error!^额度类型错误！
//    MSG_501447,//500447^modify credit sub amount is not greater than customer's credit!^减少金额必须小于会员本地额度!
//    MSG_501448,//500448^modify game credit sub amount is not greater than customer's game credit!^减少游戏金额必须小于会员本地游戏额度!
//    MSG_501449,//500449^Order number is not greater than 5 or value error!^支付单号个数不能大于5或者值个数不正确!
//    MSG_501450,//500450^currency dealer agent can't transfer credit!^三级分销币商不能转账!
//    MSG_500451,//500451^Amount not correct format,must be **.00!^金额格式不正确,格式必须为**.00!
//    MSG_500452,//500452^Amount should be greater than zero!^金额不能小于0！
//    MSG_500453,//500453^transfer out amount is not greater than customer's game credit!^转账金额不能超过会员本地游戏额度!
//    MSG_500454,//500454^Cust Type!^试玩不能转德州
//    MSG_500455,//500455^Modify request flag effect 0 rows!^修改单据状态响行数为0！
//    MSG_500456,//500456^Modify game credit exception!^修改额度异常
//    MSG_500457,//500457^WSCustomers's have unfinished credit transfer!^会员有未处理的额度互换提案!
//    MSG_500458,//500458^The transfer credit must less than customer current game credit!^转账金额不能超过会员当前游戏额度！
//    MSG_500459,//500459^The transfer credit must less than customer current game tlb credit!^转账金额不能超过会员当前贵宾厅额度！
//    MSG_500460,//500460^Create creditTransfer failed!^创建额度转账失败！
//    MSG_500461,//500461^The user's preferential system flag cannot be empty.^用户的优惠标识不能为空
//    MSG_500462,//500462^The promotion system flag of the transfer-in and transfer-out user must always be the same.^转入转出用户的优惠标识必须一致
//    MSG_500463,//500463^Not new wallet users or not promotion user!^不是新钱包用户或不是优惠用户!
//    MSG_500464,//500464^WSCustomers's have unfinished withdrawal,try it later!^新增取款失败，会员有等待审核中的取款,请稍后再试!
//    MSG_500465,//500465^AMTC Agent can't withdraw!^AMTC 代理不能取款!
//    MSG_500466,//500466^currency dealer agent can't withdraw!^三级分销币商不能取款!
//    MSG_500467,//500467^AMTC Channel Mapper can't withdraw!^AMTC 渠道商不能取款!
//    MSG_500468,//500468^Bank Account No does not match the customer!^取款银行卡不存在或与本人不匹配!
//    MSG_500469,//500469^BTC withdraw sum is more than {0} times deposit sum !^比特币取款总额超过比特币存款总额{0}倍!
//    MSG_500470,//500470^web apply withdrawal must be himself!^网站取款必须为会员本人!
//    MSG_500471,//500471^BTC Deposit be 0!^比特币存款为0!
//    MSG_500472,//500472^Backup bank error, please contact customer service!^备份银行错误,请联系客服
//    MSG_500473,//500473^The withdrawal approver can not be the same person as the credit change approver!^取款审批人和额度修改人不能相同!
//    MSG_500474,//500474^potentialVipCustomer is not exist^沉默激活客户 id 不存在!
//    MSG_500475,//500475^silenceActivateCustomer cannot be empty^客户名单id不能为空值!
//    MSG_500476,//500476^RoleId:{0} flag update fail!^角色状态更新失败!
//    MSG_500477,//500477^admin can't update flag!^超级管理员不允许修改状态!
//    MSG_500478,//500478^DimensionKey is incorrect!^维度不正确
//    MSG_500479,//500479^Only the main currency account can bind the domain name!^只有主币种账号可以绑定域名!
//    MSG_500480,//500480^Both id and referenceId cannot be empty in the same time!^id 和 referenceId 不能同时为空值!
//    MSG_500481,//500481^Can't find credit exception logs to update!^找不到对账记录!
//    MSG_500482,//500482^Less than the total integral reduction has been using the integral!^本次兑换的积分数额大于积分余额！
//    MSG_500483,//500483^login name/transfer category/transfer code/create date cannot empty at same time^登录名/转账大类/转账代码/创建时间不能同时为空!
//    MSG_500484,//500484^CreatedDate Should not be empty^创建时间不能为空
//    MSG_500485,//500485^query range limit:90 days!^查询范围限定为：90 天!
//    MSG_500486,//500486^The type of modification is incorrect!^修改贵宾厅额度类型不正确！
//    MSG_500487,//500487^Flag not correct format,must be number!^Flag标识格式不正确,格式必须为 number!
//    MSG_500488,//500488^CustomerID should not be empty,and length must for 2 to 22 between!^会员ID不能为空,长度必须在 2和 22之间
//    MSG_500489,//500489^ReferenceID should not be empty,and length must for 2 to 32 between!^ReferenceID不能为空,长度必须在 2和 32之间
//    MSG_500490,//500490^Amount must be greater than zero,format must be **.00)!^金额必须大于0,格式必须为**.00)
//    MSG_500491,//500491^loginName too much!^登录名传入过多!
//    MSG_500492,//500492^MonthWeeks not correct format,must be number!^MonthWeeks格式不正确,格式必须为 number!
//    MSG_500493,//500493^UpdateDepositFlag not correct format,must be number!^UpdateDepositFlag格式不正确,格式必须为 number!
//    MSG_500494,//500494^UpdateBetFlag not correct format,must be number!^有效投注额更新标识格式不正确,格式必须为 number!
//    MSG_500495,//500495^VipFlag not correct format,must be number!^VIP标识格式不正确,格式必须为 number!
//    MSG_500496,//500496^VipJoinDate0Begin not correct format,must be yyyy-MM-dd HH:mm:ss!^加入普通VIP时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500497,//500497^VipJoinDate0End not correct format,must be yyyy-MM-dd HH:mm:ss!^加入普通VIP时间止格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500498,//500498^VipJoinDate1Begin not correct format,must be yyyy-MM-dd HH:mm:ss!^加入超级VIP时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500499,//500499^VipJoinDate1End not correct format,must be yyyy-MM-dd HH:mm:ss!^加入超级VIP时间止格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500500,//500500^excludeTaskNo is not exist!^排除订单编号不存在!
//    MSG_500501,//500501^exist request that is pending!^用户的本银行卡存在未处理的银行提案！
//    MSG_500502,//500502^Unable to Reject Bank Request!^银行提案拒绝审批失败!
//    MSG_500503,//500503^BankRequest can't repeat!^银行提案不能重复!
//    MSG_500504,//500504^count cannot be null!^数量不能为空!
//    MSG_500507,//500507^MaxBetAmount must be a positive integer, the length is more than 16!^最大有效投注额必须是一个正整数,长度不超过16
//    MSG_500508,//500508^Add data may not be repeated!^添加的数据不能重复
//    MSG_500509,//500509^PRODUCT_ID,CUSTOMER_LEVEL,BET_AMOUNT Add data may not be repeated!^根据PRODUCT_ID,CUSTOMER_LEVEL,BET_AMOUNT 添加的数据不能重复
//    MSG_500510,//500510^one of Province and  City should not be empty!^省份和城市不能同时为空不能为空!
//    MSG_500511,//500511^Bond Old Context should not be empty!^旧绑定内容不得为空值!
//    MSG_500512,//500512^The pageSize should not be smaller than 0 !^每页显示数量不能小于等于0！
//    MSG_500513,//500513^Product Constants Is Exist^产品常量已经存在
//    MSG_500514,//500514^Type not right,must be 0001,0002,0003,0004,00099,0005,0006,0007,0008,0009,0010,0011,0012,0013,0014 one of!^常量类型不正确的值,值只能为0001,0002,0003,0004,00099,0005,0006,0007,0008,0009,0010,0011,0012,0013,0014其中之一
//    MSG_500515,//500515^Empty or more than 1 records^没有记录或者超过1条记录
//    MSG_500516,//500516^PRODUCT_ID,GAME_ID,GAME_KIND Add data may not be repeated^根据PRODUCT_ID,GAME_ID,GAME_KIND 添加的数据不能重复
//    MSG_500517,//500517^ProductName can't repeat!^产品名不能重复
//    MSG_500518,//500518^ProductId can't repeat!^产品ID不能重复!
//    MSG_500519,//500519^Format of RATE not correct,must be **.00^洗码佣金比率格式不正确,格式必须为**.00
//    MSG_500520,//500520^PRODUCT_ID,CUSTOMER_LEVEL,CUSTOMER_TYPE,GAME_ID,GAME_KIND Add data may not be repeated^根据PRODUCT_ID,CUSTOMER_LEVEL,CUSTOMER_TYPE,GAME_ID,GAME_KIND 添加的数据不能重复
//    MSG_500521,//500521^PRODUCT_ID,CUSTOMER_TYPE Add data may not be repeated^根据PRODUCT_ID,CUSTOMER_TYPE 添加的数据不能重复
//    MSG_500522,//500522^Credit Modification Request Failed To Approve!^额度修正提案通过失败!
//    MSG_500523,//500523^The pageNum should not be smaller than 0 !^页面数字不能小于等于0！
//    MSG_500524,//500524^Credit Modification Request Failed To Reject!^额度修正提案拒绝失败!
//    MSG_500525,//500525^IsShow should be number type!^前台是否显示的类型只能为数字类型！
//    MSG_500526,//500526^IsShow is more than one length!^前台是否显示的长度只能为1个长度！
//    MSG_500527,//500527^email Request Failed To Approve!^邮箱更改提案审批错误!
//    MSG_500528,//500528^email Request Failed To Reject!^邮箱更改提案驳回错误!
//    MSG_500529,//500529^email Request Failed To Audit!^邮箱更改提案审核错误!
//    MSG_500530,//500530^Level Request is the same with the current Level info!^提案修改的等级和会员现在的等级相同!
//    MSG_500531,//500531^Parent ID request is the same with the current Parent ID info!^parentId跟当前parentId不能一样！
//    MSG_500532,//500532^Customer has been active!^激活状态，已激活!
//    MSG_500533,//500533^WSCustomers's have unfinished Modify parent Request!^会员有未处理的修改上线提案!
//    MSG_500534,//500534^Case Active Status!^案例活跃状态
//    MSG_500535,//500535^the customer is to-deal status in TMCase,not allow to update TM_Parent!^该用户在电销名单中为待处理状态，不允许修改电销上级
//    MSG_500536,//500536^parent must update!^会员上线必须改变!
//    MSG_500537,//500537^WSCustomers's have unfinished same type modify Account!^会员有未处理的修改提案!
//    MSG_500538,//500538^customer Account status must update!^相同状态不能修改!
//    MSG_500539,//500539^SerialType is incorrect, the value must in 1,2,3 one of!^单号生成规则类型不正确，必须在1,2,3之中！
//    MSG_500540,//500540^Maximum number of generations exceeded per minute!^超出单位分钟最大生成数！
//    MSG_500541,//500541^The number of batch generated requestId is incorrect and the value must be between 2 and {0}!^批量生成单号的数量不正确，值必须介于2-{0}之间！
//    MSG_500542,//500542^MaxDeposit must be a positive integer, the length is more than 9!^MaxDeposit必须是一个正整数,长度不超过9
//    MSG_500543,//500543^MinDeposit must be a positive integer, the length is more than 9!^MinDeposit必须是一个正整数,长度不超过9
//    MSG_500544,//500544^MaxAmountTime must be a positive integer, the length is more than 9!^单次最大限额必须是一个正整数,长度不超过9
//    MSG_500545,//500545^sendSms must be a positive integer, the length is more than 9!^是否发送短信提醒必须是一个正整数,长度不超过9
//    MSG_500546,//500546^Exception happend during check endpointType!^校验终端类型出现异常！
//    MSG_500547,//500547^Start date must less than end date!^开始时间必须小于结束时间！
//    MSG_500548,//500548^EndpointType must in {0}!^终端类型必须在 {0}之中！
//    MSG_500549,//500549^limitRelations must be a positive integer, the length is more than 9!^约束关系必须是一个正整数,长度不超过9
//    MSG_500550,//500550^MinDeposit must be a positive integer, the length is more than 9!^最小存款(投注)金额必须是一个正整数,长度不超过9
//    MSG_500551,//500551^MaxDeposit must be a positive integer, the length is more than 9!^最大存款(投注)金额必须是一个正整数,长度不超过9
//    MSG_500552,//500552^Promotion Config ID  not exist or is disable!^优惠配置ID不存在或已失效
//    MSG_500553,//500553^Exception happened during check endpointType!^校验终端类型出现异常！
//    MSG_500554,//500554^Promotion Mutex,Received Other Promotion !^优惠互斥,已领取其它优惠!
//    MSG_500555,//500555^groupKey is not exist!^groupKey 不存在!
//    MSG_500556,//500556^CardNum should not be empty!^卡号或号码不能为空!
//    MSG_500557,//500557^phone number is empty!^电话为空
//    MSG_500558,//500558^The maxium number of per page is {0}!^每页最多显示{0}行!
//    MSG_500559,//500559^Customer has pending reservation^客户有待处理的提案
//    MSG_500560,//500560^Customer not has pending reservation^客户没有待处理的提案
//    MSG_500561,//500561^XM Period Begin Time  should be xm start date^周期开始时间应该为洗码周期开始日期!
//    MSG_500562,//500562^XM Period Begin Time should not after Period End Time!^周期开始时间不可以晚于结束时间!
//    MSG_500563,//500563^XM Period End Time should not after Period End Time!^周期结束时间应该为洗码周期结束日期！
//    MSG_500564,//500564^XM Period should not over 7 days!^周期时间不可以超过7天!
//    MSG_500565,//500565^Format of Bank Account No not correct^客户银行账号格式不正确
//    MSG_500566,//500566^This nickName has already been registered!^该昵称已经被注册不能重复!
//    MSG_500567,//500567^Nickname can only be Chinese!^昵称只能输入中文!
//    MSG_500568,//500568^title length must for 0 to 20 between!^拨打主题 长度必须在0和20之间!
//    MSG_500569,//500569^callTime  should not be empty!^拨打时间不能为空!
//    MSG_500570,//500570^conditionType is 1 loginNameStr must not be empty!^拨打条件类型为1时登录名不允许为空!
//    MSG_500571,//500571^conditionType is 2 customerLevelStr and depositLevelStr must not be both empty!^拨打条件类型为2时用户星级和用户信用级不允许同时为空!
//    MSG_500572,//500572^conditionType is 3 phoneStr must not be empty!^拨打条件类型为3时电话不允许为空!
//    MSG_500573,//500573^records has exist!^专线线路值已存在!
//    MSG_500574,//500574^Credit not correct format,must be **.00^会员额度格式不正确，必须为**.00
//    MSG_500575,//500575^LastLoginDateBegin not correct format,must be yyyy-MM-dd HH:mm:ss!^最后登录时间起格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500576,//500576^LastLoginDateEnd not correct format,must be yyyy-MM-dd HH:mm:ss!^最后登录时间止格式不正确,格式必须为 yyyy-MM-dd HH:mm:ss!
//    MSG_500577,//500577^PriorityLevel not correct format,must be number!^会员优先级格式不正确,格式必须为 number!
//    MSG_500578,//500578^Invalid customer type!^修改下线有效状态失败!
//    MSG_500579,//500579^query data records must in 5000 rows!^请求数据不能超过5000条！
//    MSG_500580,//500580^phoneBindStatus or emailBindStatus can not be null!^phoneBindStatus、emailBindStatus 必须有一个不为空！
//    MSG_500581,//500581^StatisticsDate query range limit:90days!^统计时间查询范围限定为:90天!
//    MSG_500582,//500582^StatisticsDate Should not be empty^统计时间不能为空!
//    MSG_500583,//500583^Expired date must bigger than now!^失效日期必须大于当前日期！
//    MSG_500584,//500584^Expired date must bigger than effect date!^失效日期必须大于生效日期！
//    MSG_500585,//500585^Expirydate should not be less than current date!^公告过期时间必须大于当前天！
//    MSG_500586,//500586^EffectivityDate must be less than the expiration time!^公告生效时间必须小于过期时间！
//    MSG_500589,//500589^Task ID should not be empty!^任务ID不能为空!
//    MSG_500590,//500590^CustomerId, TaskId，loginName cannot be empty at the same time!^客户id,任务id,客户登录名不能同时为空!
//    MSG_500591,//500591^WSCustomers's have unfinished Modify suggest Request!^会员有未处理的提案!
//    MSG_500592,//500592^encrypt content failed!^设置加密content失败
//    MSG_500593,//500593^Exist same config!^存在相同的配置！
//    MSG_500594,//500594^the same type result can't repeat can't repeat!^同一类型的结果不能重复!
//    MSG_500595,//500595^Amount1 must be a positive integer, the length is more than 5!^第10奖项金额必须是一个正整数,长度不超过5!
//    MSG_500596,//500596^Num10 must be a positive integer, the length is more than 3!^第10奖项数量必须是一个正整数,长度不超过3!
//    MSG_500597,//500597^Multiplier10 must be a positive integer, the length is more than 9!^第10奖项中奖后有效投注额金额必须是一个正整数,长度不超过9!
//    MSG_500598,//500598^Num11 must be a positive integer, the length is more than 3!^第11奖项数量必须是一个正整数,长度不超过3!
//    MSG_500599,//500599^Multiplier11 must be a positive integer, the length is more than 9!^第11奖项中奖后有效投注额金额必须是一个正整数,长度不超过9
//    MSG_500600,//500600^RequestId should not be empty,and length must be fixed 19!^提案请求ID不能为空,长度必须为19!
//    MSG_500601,//500601^Amount1 must be a positive integer, the length is more than 5!^第12奖项金额必须是一个正整数,长度不超过5!
//    MSG_500602,//500602^Num12 must be a positive integer, the length is more than 3!^第12奖项数量必须是一个正整数,长度不超过3!
//    MSG_500603,//500603^Multiplier12 must be a positive integer, the length is more than 9!^第12奖项中奖后有效投注额金额必须是一个正整数,长度不超过9!
//    MSG_500604,//500604^Amount1 must be a positive integer, the length is more than 5!^第13奖项金额必须是一个正整数,长度不超过5!
//    MSG_500605,//500605^Num13 must be a positive integer, the length is more than 3!^第13奖项数量必须是一个正整数,长度不超过3!
//    MSG_500606,//500606^Multiplier13 must be a positive integer, the length is more than 9!^第13奖项中奖后有效投注额金额必须是一个正整数,长度不超过9!
//    MSG_500607,//500607^Amount1 must be a positive integer, the length is more than 5!^第14奖项金额必须是一个正整数,长度不超过5!
//    MSG_500608,//500608^Num14 must be a positive integer, the length is more than 3!^第14奖项数量必须是一个正整数,长度不超过3!
//    MSG_500609,//500609^Multiplier14 must be a positive integer, the length is more than 9!^第14奖项中奖后有效投注额金额必须是一个正整数,长度不超过9!
//    MSG_500610,//500610^Amount1 must be a positive integer, the length is more than 5!^第15奖项金额必须是一个正整数,长度不超过5!
//    MSG_500611,//500611^Num15 must be a positive integer, the length is more than 3!^第15奖项数量必须是一个正整数,长度不超过3!
//    MSG_500612,//500612^Multiplier15 must be a positive integer, the length is more than 9!^第15奖项中奖后有效投注额金额必须是一个正整数,长度不超过9!
//    MSG_500613,//500613^Pwd should not be empty,and length must be fixed 32!^密码不能为空,长度必须为32!
//    MSG_500614,//500614^Role{0} does not exist^角色{0}不存在!
//    MSG_500615,//500615^BranchCode is not exists!^门店code不存在!
//    MSG_500616,//500616^role should not be empty!^角色不能为空!
//    MSG_500617,//500617^Login name must to lowercase letters and dot and Numbers composition!^登录名必须为小写字母和字符.和数字组成!
//    MSG_500618,//500618^System-Account already exist!^系统账户已存在!
//    MSG_500619,//500619^System-Account is incorrect!^登录名输入不正确！
//    MSG_500620,//500620^Vip Type already existed!^Vip Type 已存在!
//    MSG_500621,//500621^Range of MinLevel And Maxlevel already existed !^最大最小星级与已有VIP TYPE重合!
//    MSG_500622,//500622^Transfer out interest amount not normal!^转出产生的利息金额异常！
//    MSG_500623,//500623^Transfer amount must more than zero!^转出金额或过夜利息钱包额度必须大于0
//    MSG_500624,//500624^Transfer out amount must less than or equals current yeb amount!^转出金额必须小于yeb额度！
//    MSG_500625,//500625^Transfer out interest amount must less than or equals current yeb interest amount!^转出利息金额必须小于yeb利息额度!
//    MSG_500626,//500626^Interest amount is correct!^利息额度不正确!
//    MSG_500627,//500627^Transfer Code Is Invalid!^转账代号无效!
//    MSG_500628,//500628^RoleName can't repeat!^角色名不能重复!
//    MSG_500629,//500629^RoleId not exist!^角色不存在!
//    MSG_500630,//500630^ParentRoleId not exist!^父角色不存在!
//    MSG_500631,//500631^Currency is not consistent must be greater than zero,format must be **.000000!^输入的货币与客户的不一致必须大于0,格式必须为**.000000!
//    MSG_500632,//500632^CustomerType not right,must be U,C one of!^会员类型不正确的值,值只能为U,C其中之一
//    MSG_500633,//500633^Currency Type^货币种类!
//    MSG_500634,//500634^Trial account can't the operator^试玩会员不能进行该操作!
//    MSG_500635,//500635^WSCustomers's have unfinished withdrawal,try it later!^会员有未处理的取款提案,请稍后再试!
//    MSG_500636,//500636^Confirm number is not config^比特币确认次数未配置!
//    MSG_500637,//500637^Withdraw amount must not exceed local amount!^取款金额不能超过会员本地额度!
//    MSG_500638,//500638^Tlb transfer audit has been deprecated!^贵宾厅转账审批功能已废弃！
//    MSG_500639,//500639^RequestId can't repeat!^提案请求ID不能重复!
//    MSG_500640,//500640^Newflag not right,must be 0, 1, -2, -1, -3, 2, -5 one of!^提案新状态不正确的值,值只能为0, 1, -2, -1, -3, 2, -5其中之一!
//    MSG_500641,//500641^Newflag not right,must be 1, -2, -1, -3, 2 one of!^提案新状态不正确的值,值只能为1, -2, -1, -3, 2其中之一!
//    MSG_500642,//500642^Newflag not right,must be 4 one of!^提案新状态不正确的值,值只能为4其中之一!
//    MSG_500643,//500643^old flag not right,must be 0,1,-5 one of!^提案旧状态不正确的值,值只能为0,1,-5其中之一!
//    MSG_500644,//500644^TM Case Id should not be empty!^TM Case Id不能为空!
//    MSG_500645,//500645^Call Duration should not be empty!^Call Duration不能为空!
//    MSG_500646,//500646^Call End should not be empty!^Call End 不能为空!
//    MSG_500647,//500647^Call Start should not be empty!^Call Start不能为空!
//    MSG_500648,//500648^Call Validity should not be empty!^Call Validity不能为空!
//    MSG_500649,//500649^Case Reason should not be empty!^Case Reason不能为空!
//    MSG_500650,//500650^Customer Response should not be empty!^Customer Response不能为空!
//    MSG_500651,//500651^Telemarketer should not be empty!^Telemarketer不能为空!
//    MSG_500652,//500652^Deleted By should not be empty!^Deleted By不能为空!
//    MSG_500653,//500653^IDs should not be empty!^Id's不能为空!
//    MSG_500655,//500655^Delete Remarks should not be empty!^Delete Remarks不能为空!
//    MSG_500656,//500656^Delete Flag should not be empty!^Delete Flag不能为空!
//    MSG_500657,//500657^Assigned By should not be empty!^Assigned By不能为空!
//    MSG_500658,//500658^Call Log ID should not be empty!^Call Log ID不能为空!
//    MSG_500659,//500659^LastUpdatedBy not exist!^最后修改人不存在!
//    MSG_500660,//500660^Rebate Request Failed To Approve!^洗码审批失败!
//    MSG_500661,//500661^Invalid rebate approval status!^审批失败。无效洗码状态!
//    MSG_500662,//500662^XMdate can not bigger than end date!^洗码周期开始时间不能大于结束时间!
//    MSG_500663,//500663^The Configure XM_DATE_SET not exist!^洗码周期配置XM_DATE_SET不能为空!
//    MSG_500664,//500664^rebate startdate and enddate must between 1 - 7.^洗码周期时间差必须在必须在1-7之间!
//    MSG_500665,//500665^week and update rebate must be set endpointType!^周洗码和上传洗码必须设置终端类型!
//    MSG_500666,//500666^week and update rebate only suport endpointType!^周洗码和上传洗码仅支持office提交!
//    MSG_500667,//500667^BettingAmount not correct format,must be **.00!^有效投注额不正确,格式必须为**.00!
//    MSG_500668,//500668^total amount not equals list amount.^总洗码金额和分类和不相等!
//    MSG_500669,//500669^MinBetAmount must be a positive integer, the length is more than 16!^最小有效投注额必须是一个正整数,长度不超过16
//    MSG_500670,//500670^The interface statistics type incorrect!^接口统计类型不正确！
//    MSG_500671,//500671^birthDate is less than current time^出生日期小于当前时间
//    MSG_500672,//500672^birthDate is less than 21 years old^出生日期小于21岁
//    MSG_500673,//300814^BranchName Please enter the correct account opening outlet!^请输入正确开户网点
//    MSG_381001,//381001^Customer doesn't exist!^客户不存在！
//    MSG_381002,//381002^errCode already existed!^错误码已存在！
//    MSG_381003,//381003^firstIdType already existed!^firstIdType已存在！
//    MSG_381004,//381004^SecondIdType already existed!^SecondIdType已存在！
//    MSG_381005,//500363^WSCustomers's have unfinished Modify Identity Request!^会员有未处理的修改证件提案!
//    MSG_381006,//381006^firstIdType not be empty!^firstIdType不能为空！
//    MSG_381007,//381007^firstNoType not be empty!^firstNoType不能为空！
//    MSG_381008,//381008^firstNoType already existed!^证件号已存在！
//    MSG_381009,//381009^Proposal for application for existence!^证件号正在申请提案！
//    MSG_381010,//381010^identity modityType must be {0} 其中之一!^证件变更的修改类型必须为{0}！
//    MSG_381011,//381011^firstIdScan not be empty!^第一个证件号key不能为空！
//    MSG_381012,//381012^secondIdScan not be empty!^第二个证件号key不能为空！
//    MSG_381013,//381013^firstNoType cannot contain special characters!^证件号不能包含特殊字符串！
//    MSG_381014,//381014^firstName should not be empty,the length must be between {0} to {1}!^firstName长度只能在{0}与{1}之间！
//    MSG_381015,//381015^lastName should not be empty,the length must be between {0} to {1}!^lastName长度只能在{0}与{1}之间！
//    MSG_381016,//381016^Phone not correct format,Must be a number from 8-16^会员电话格式不正确,必须为8-16的数字
//    MSG_381017,//381017^Phone not correct format,Must be a number^会员电话格式不正确,必须为数字
//    MSG_381018,//381018^Please enter 12 digits^仅为12位数字
//    MSG_381019,//381019^Please enter 9 alphanumeric characters^仅为9个字符，字母+数字
//    MSG_381020,//381020^Please enter {0}-{1} digits^证件号必须为{0} - {1}位数字
//    MSG_381021,//381021^The ID number must be {0}-{1} characters, letters + numbers^证件号必须为{0}-{1}个字符，字母+数字
//    MSG_381022,//381022^branch name duplicate
//    MSG_381023,//381023^Already joined other teams
//    MSG_381024,//381024^This invitation does not existed
//    MSG_381025,//381025^The team is full
//    MSG_381026,//381026^Cannot repeat invitation
//    MSG_381027,//381027^XXX has joined another band, please try to invite again next month.
//    MSG_381028,//381028^You have failed to join XXX's band, as the band is full. Please try to join next month.
//    MSG_381029,//381029^,Your band is full,please try to dismiss your band and invite next month.
//    MSG_381030,//381030^You had invited XXX,please wait for the respond.
//    MSG_381031,//381031^Customer Certification exists.
//    MSG_381032,//381032^GameCode cannot be empty.
//    MSG_381033,//381033^CreatedDate query range limit:7days!
//    MSG_381034,//381034^Date query range limit:1days!
//    MSG_381035,//381035^TransferGameInit failed to get billNo.
//    MSG_381036,//381036^BankAccountName limit 0~60.
//    MSG_381037,//381037^Date query range limit: 7 days!
//    MSG_381038,//381038^BillNo's count limited to less than 1000!
//    MSG_381042,//381042^CreatedDate Time query range limit: 31 days!
//    ;
//
//    public String getErrMsg(Object... values) {
//        return getErrMsgEn(values);
//    }
//
//    public String getErrMsgEn(Object... values) {
//        return MessageFormat.format(LocalCacheUtil.getErr(this.name()).getEn(), values);
//    }
//
//    public String getErrCode(Object... values) {
//        return MessageFormat.format(LocalCacheUtil.getErr(this.name()).getErrCode(), values);
//    }
//
//    @Override
//    public Boolean getSuccess(Object... actualParams) {
//        return ResultEnum.SUCCESS.getCode().equals(LocalCacheUtil.getErr(this.name()).getErrCode());
//    }
//
//    @Override
//    public String getCode(Object... actualParams) {
//        return LocalCacheUtil.getErr(this.name()).getErrCode();
//    }
//
//    @Override
//    public String getMessage(Object... actualParams) {
//        return MessageFormat.format(LocalCacheUtil.getErr(this.name()).getEn(), actualParams);
//    }
//}

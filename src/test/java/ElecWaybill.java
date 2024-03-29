import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kuaidi100.sdk.api.*;
import com.kuaidi100.sdk.contant.ApiInfoConstant;
import com.kuaidi100.sdk.contant.CompanyConstant;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.pojo.HttpResult;
import com.kuaidi100.sdk.request.*;
import com.kuaidi100.sdk.response.BaseResponse;
import com.kuaidi100.sdk.response.ThirdPlatformRestData;
import com.kuaidi100.sdk.utils.SignUtils;
import org.junit.Test;

import java.util.List;

/**
 * 电子面单测试用例
 *
 * @Author: api.kuaidi100.com
 * @Date: 2021-03-22 18:55
 */
public class ElecWaybill extends BaseServiceTest{

    /**
     * 电子面单图片接口
     */
    @Test
    public void testPrintImg() throws Exception{
        PrintImgParam printImgParam = new PrintImgParam();
        printImgParam.setKuaidicom(CompanyConstant.SF);
        printImgParam.setSendManName("张三");
        printImgParam.setSendManMobile("15999566666");
        printImgParam.setSendManPrintAddr("广东省深圳市南山区科技南十二路");
        printImgParam.setRecManName("李四");
        printImgParam.setRecManMobile("15999566666");
        printImgParam.setRecManPrintAddr("北京市海淀区xxx路");
        printImgParam.setType("10");
        printImgParam.setTempid("180c7c8f646742ca871a92c976392b05");
        printImgParam.setCount("1");

        String param = new Gson().toJson(printImgParam);
        String t = System.currentTimeMillis() + "";
        String sign = SignUtils.printSign(param,t,key,secret);

        PrintReq printReq = new PrintReq();
        printReq.setKey(key);
        printReq.setMethod(ApiInfoConstant.ELECTRONIC_ORDER_PIC_METHOD);
        printReq.setSign(sign);
        printReq.setParam(param);
        printReq.setT(t);

        IBaseClient printImg = new PrintImg();
        System.out.println(printImg.execute(printReq));
    }

    /**
     * 电子面单html接口
     */
    @Test
    public void testPrintHtml() throws Exception{
        ManInfo recManInfo  = new ManInfo();
        recManInfo.setName("张三");
        recManInfo.setMobile("15999566666");
        recManInfo.setPrintAddr("广东省深圳市南山区科技南十二路");

        ManInfo sendManInfo = new ManInfo();
        sendManInfo.setName("李四");
        sendManInfo.setMobile("15999566666");
        sendManInfo.setPrintAddr("北京市海淀区xxx路");

        PrintHtmlParam printHtmlParam = new PrintHtmlParam();
        printHtmlParam.setKuaidicom(CompanyConstant.SF);
        printHtmlParam.setCount("2");
        printHtmlParam.setNeedChild("1");
        //如果需要返回电子面单，需要设置
        printHtmlParam.setNeedTemplate("0");
        printHtmlParam.setSendMan(sendManInfo);
        printHtmlParam.setRecMan(recManInfo);

        String t = System.currentTimeMillis() + "";
        String param = new Gson().toJson(printHtmlParam);
        String sign = SignUtils.printSign(param,t,key,secret);

        PrintReq printReq = new PrintReq();
        printReq.setKey(key);
        printReq.setMethod(ApiInfoConstant.ELECTRONIC_ORDER_HTML_METHOD);
        printReq.setT(t);
        printReq.setSign(sign);
        printReq.setParam(param);

        IBaseClient printHtml = new PrintHtml();
        System.out.println(printHtml.execute(printReq));
    }

    /**
     * 电子面单打印
     */
    @Test
    public void testPrintCloud() throws Exception{
        ManInfo recManInfo  = new ManInfo();
        recManInfo.setName("张三");
        recManInfo.setMobile("15999566666");
        recManInfo.setPrintAddr("广东省深圳市南山区科技南十二路");

        ManInfo sendManInfo = new ManInfo();
        sendManInfo.setName("李四");
        sendManInfo.setMobile("15999566666");
        sendManInfo.setPrintAddr("北京市海淀区xxx路");

        PrintCloudParam printCloudParam = new PrintCloudParam();
        printCloudParam.setKuaidicom(CompanyConstant.ZJS);
        printCloudParam.setCount("1");
        printCloudParam.setSiid(siid);
        printCloudParam.setTempid("180c7c8f646742ca871a92c976392b05");
        printCloudParam.setSendMan(sendManInfo);
        printCloudParam.setRecMan(recManInfo);

        String param = new Gson().toJson(printCloudParam);
        String t = System.currentTimeMillis() + "";

        PrintReq printReq = new PrintReq();
        printReq.setT(t);
        printReq.setKey(key);
        printReq.setMethod(ApiInfoConstant.ELECTRONIC_ORDER_PRINT_METHOD);
        printReq.setSign(SignUtils.printSign(param,t,key,secret));
        printReq.setParam(param);

        IBaseClient baseClient = new PrintCloud();
        System.out.println(baseClient.execute(printReq));
    }

    /**
     * 面单取消（部分支持，详情请查看参数字典）
     */
    @Test
    public void testLabelCancel() throws Exception{
       LabelCancelParam labelCancelParam = new LabelCancelParam();
       labelCancelParam.setPartnerId("test");
       labelCancelParam.setKuaidicom(CompanyConstant.SF);
       labelCancelParam.setKuaidinum("SF1342567604302");
       //快递公司订单号(对应下单时返回的kdComOrderNum，如果没有可以不传，否则必传)
       labelCancelParam.setOrderId("01639366271685GNkZEX");

       labelCancelParam.setReason("暂时不寄了");
        String param = new Gson().toJson(labelCancelParam);
        String t = System.currentTimeMillis() + "";

        PrintReq printReq = new PrintReq();
        printReq.setT(t);
        printReq.setKey(key);
        printReq.setMethod(ApiInfoConstant.CANCEL_METHOD);
        printReq.setSign(SignUtils.printSign(param,t,key,secret));
        printReq.setParam(param);

        IBaseClient baseClient = new LabelCancel();
        System.out.println(baseClient.execute(printReq));
    }

    /**
     * 第三方平台网点&面单余额接口
     */
    @Test
    public void testRest() throws Exception{
        ThirdPlatformRestReq thirdPlatformRestReq = new ThirdPlatformRestReq();
        thirdPlatformRestReq.setPartnerId("123456");
        thirdPlatformRestReq.setCom(CompanyConstant.SF);
        thirdPlatformRestReq.setPartnerKey("123456");
        thirdPlatformRestReq.setNet("jdalpha");
        String param = new Gson().toJson(thirdPlatformRestReq);
        String t = System.currentTimeMillis() + "";

        PrintReq printReq = new PrintReq();
        printReq.setT(t);
        printReq.setKey(key);
        printReq.setMethod(ApiInfoConstant.THIRD_PLATFORM_REST);
        printReq.setSign(SignUtils.printSign(param,t,key,secret));
        printReq.setParam(param);

        IBaseClient baseClient = new ThirdPlatformRest();

        HttpResult result = baseClient.execute(printReq);
        BaseResponse<List<ThirdPlatformRestData>> resp= new Gson().fromJson(result.getBody(), new TypeToken<BaseResponse<List<ThirdPlatformRestData>>>() {
        }.getType());
        System.out.println(result);
    }
}

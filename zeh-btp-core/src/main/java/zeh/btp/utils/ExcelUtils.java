package zeh.btp.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Excel工具类
 */
public class ExcelUtils {

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        String oldContentType = response.getContentType();
        try {
            // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, String.valueOf(StandardCharsets.UTF_8)));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            // 输出 Excel
            EasyExcel.write(response.getOutputStream(), head)
                    .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                    .registerConverter(new LongStringConverter()) // 避免 Long 类型丢失精度
                    .sheet(sheetName).doWrite(data);
        } catch (Exception e) {
            response.reset();
            response.setContentType(oldContentType);
            throw e;
        }
    }

    /**
     * 读取文件内容为指定实体列表
     *
     * @param file file
     * @param head class instance
     * @param <T>  泛型
     * @return 实体列表
     * @throws IOException io异常
     */
    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

    /**
     * 读取流为指定实体列表
     *
     * @param stream
     * @param head
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> read(InputStream stream, Class<T> head) throws IOException {
        return EasyExcel.read(stream, head, null)
                .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

}

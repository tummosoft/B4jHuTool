package poidemo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.word.Word07Writer;
import java.awt.Font;

public class POIDemo {

    public static void main(String[] args) {
        // 2. Thêm nội dung văn bản
        // Thêm tiêu đề (Heading)
        try ( // 1. Khởi tạo Writer
                Word07Writer writer = new Word07Writer()) {
            // 2. Thêm nội dung văn bản
            // Thêm tiêu đề (Heading)
            Font font = new Font("Arial", Font.BOLD, 18);
            writer.addText(font, "BÁO CÁO DỰ ÁN HÀNG THÁNG");
            // Thêm một đoạn văn bản thường
            writer.addText(new Font("Times New Roman", Font.PLAIN, 12),
                    "Đây là tài liệu được tạo tự động bằng thư viện Hutool trong Java.");
            // 3. Thêm bảng biểu (Table)
            // Tạo dữ liệu cho bảng
            writer.addTable(CollUtil.newArrayList(
                    CollUtil.newArrayList("STT", "Tên Công Việc", "Trạng Thái"),
                    CollUtil.newArrayList("1", "Thiết kế Database", "Hoàn thành"),
                    CollUtil.newArrayList("2", "Viết API Backend", "Đang thực hiện"),
                    CollUtil.newArrayList("3", "Kiểm thử (Unit Test)", "Chưa bắt đầu")
            )); // 4. Xuất file ra đường dẫn cụ thể
            writer.flush(FileUtil.file("d:/demo_hutool.docx"));
            // 5. Đóng writer để giải phóng tài nguyên
        }
        
        System.out.println("Tạo file Word thành công!");
    }
    
}

package io.github.septillions.examples.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import java.io.File;

public class Mp3tagUtils {

    /**
     * LosslessCut 合并 MP3 文件后，根据源文件信息 补齐 Id3v2Tag
     */
    public static void main(String[] args) throws Exception {
        // 操作文件夹
        String operationDir = "D:\\Music\\LosslessCut";
        // 原始文件夹
        String originDir = "D:\\迅雷下载\\8BitStereo";
        // 导出文件夹
        String exportDir = "D:\\Music\\8BitStereo";

        File operation = new File(operationDir);
        File[] operationFiles = operation.listFiles();

        for (File operationFile : operationFiles) {
            String originFileDir = originDir + "\\" + StrUtil.removeSuffix(operationFile.getName(), ".mp3");
            if (!new File(originFileDir).isDirectory()) {
                // 无原始文件夹 跳过
                continue;
            }
            File originFile = new File(originFileDir).listFiles()[0];
            // 操作文件信息
            Mp3File operationMp3 = new Mp3File(operationFile);
            if (StrUtil.isNotBlank(operationMp3.getId3v2Tag().getTitle())) {
                // 有标题信息 跳过
                continue;
            }
            operationMp3.removeId3v2Tag();
            ID3v2 operationTag = new ID3v24Tag();
            operationMp3.setId3v2Tag(operationTag);
            // 原始文件信息
            Mp3File originMp3 = new Mp3File(originFile);
            ID3v2 originTag = originMp3.getId3v2Tag();
            // 标题
            String title = operationFile.getName().split(" - ")[0] + " - " + originTag.getAlbum().split(" - ")[1];
            operationTag.setTitle(title);
            // 艺术家
            String artist = originTag.getArtist();
            operationTag.setArtist(artist);
            // 唱片集
            String album = StrUtil.removePrefix(originTag.getAlbum(), "8BitStereo - ");
            operationTag.setAlbum(album);
            // 年份
            String year = originTag.getYear();
            operationTag.setYear(year);
            // 音轨号
            String track = "1";
            operationTag.setTrack(track);
            // 流派
            String genreDescription = originTag.getGenreDescription();
            operationTag.setGenreDescription(genreDescription);
            // 作曲家
            String composer = originTag.getComposer();
            operationTag.setComposer(composer);
            // 封面
            byte[] albumImage = null;
            String mimeType = null;
            for (File file : new File(originFileDir).listFiles()) {
                String type = FileTypeUtil.getType(file);
                if ("png".equals(type)) {
                    albumImage = FileUtil.readBytes(file);
                    mimeType = "image/png";
                }
                if ("jpg".equals(type)) {
                    albumImage = FileUtil.readBytes(file);
                    mimeType = "image/jpeg";
                }
            }
            operationTag.setAlbumImage(albumImage, mimeType, (byte) 0x3, null);
            // 导出文件
            operationMp3.save(exportDir + "\\" + operationFile.getName());
            // 打印文件信息
            System.out.println("------");
            System.out.println("标题：" + title);
            System.out.println("艺术家：" + artist);
            System.out.println("唱片集：" + album);
            System.out.println("年份：" + year);
            System.out.println("音轨号：" + track);
            System.out.println("流派：" + genreDescription);
            System.out.println("作曲家：" + composer);
        }
        System.out.println("--- 处理完成 ---");
    }
}

package com.offcn.test;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * @author chenxi
 * @date 2021/11/5 20:57
 * @description
 */
public class Test {
    public static void main(String[] args) throws IOException, MyException {

        ClientGlobal.init("D:\\develop\\data\\dongyimai-parent\\dongyimai-service\\dongyimai-file-service\\src\\main\\resources\\fdfs_client.conf");

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer connection = trackerClient.getConnection();

        StorageClient storageClient = new StorageClient(connection,null);

        String[] strings = storageClient.upload_file("C:\\Users\\chenxi\\Desktop\\3.jpg", "jpg", null);

        for (String string : strings) {
            System.out.println(string);
        }


    }
}

package com.mhj.store.controller.ex;

/** 上传的文件的大小超出了限制值
 *  The size of the uploaded file exceeds the limit
 * */
public class FileSizeException extends FileUploadException {
    public FileSizeException() {
        super();
    }

    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }

    protected FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.mina.spider.beans;

import java.util.Map;

/**
 * Created by mina on 2018/2/6.
 */

public class DownloadUrls {

    /**
     * download url
     */
	private MovieItem movieInfo;
	
	private Map<String, String> thunderUrls;

    private Map<String, String>torrentUrls;

    private Map<String, String> magnetUrls;

    private Map<String, String> ftpUrls;

    private Map<String, String> ed2kUrls;

    public DownloadUrls(){}

    public Map<String, String> getThunderUrls() {
        return thunderUrls;
    }

    public void setThunderUrls(Map<String, String> thunderUrls) {
        this.thunderUrls = thunderUrls;
    }

    public Map<String, String> getTorrentUrls() {
        return torrentUrls;
    }

    public void setTorrentUrls(Map<String, String> torrentUrls) {
        this.torrentUrls = torrentUrls;
    }

    public Map<String, String> getMagnetUrls() {
        return magnetUrls;
    }

    public void setMagnetUrls(Map<String, String> magnetUrls) {
        this.magnetUrls = magnetUrls;
    }

    public Map<String, String> getFtpUrls() {
        return ftpUrls;
    }

    public void setFtpUrls(Map<String, String> ftpUrls) {
        this.ftpUrls = ftpUrls;
    }

    public Map<String, String> getEd2kUrls() {
        return ed2kUrls;
    }

    public void setEd2kUrls(Map<String, String> ed2kUrls) {
        this.ed2kUrls = ed2kUrls;
    }
    
    public MovieItem getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(MovieItem movieInfo) {
		this.movieInfo = movieInfo;
	}

}

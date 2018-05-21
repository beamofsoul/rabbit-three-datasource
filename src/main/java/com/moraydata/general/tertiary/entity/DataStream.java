package com.moraydata.general.tertiary.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "HM_DATA_STREAM")
public class DataStream {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(columnDefinition = "varchar(32) NOT NULL DEFAULT '' COMMENT '作者'")
	private String author;
	
	@Column(columnDefinition = "varchar(128) NOT NULL DEFAULT '' COMMENT '保留70长度'")
	private String title;
	
	@Column(name = "media_id", columnDefinition = "int(11) DEFAULT NULL")
	private Integer mediaId;
	
	@Column(name = "media_name", columnDefinition = "varchar(128) NOT NULL DEFAULT '' COMMENT '媒体名称 media_name'")
	private String mediaName;
	
	@Column(columnDefinition = "varchar(512) NOT NULL DEFAULT ''")
	private String url;
	
	@Column(name = "scenic_id", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '景区id 根据task_id从task表中找到对应的scenic_id'")
	private Long scenicId;
	
	@Column(name = "source_type", columnDefinition = "smallint(4) NOT NULL DEFAULT '-1' COMMENT '媒体类型(信源类型)")
	private Integer sourceType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "release_date", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'")
	private Date releaseDate;
	
	@Column(name = "url_crc", columnDefinition = "varchar(64) NOT NULL DEFAULT ''")
	private String urlCrc;
	
	@Column(name = "tag_ids", columnDefinition = "varchar(128) NOT NULL DEFAULT '' COMMENT '标签维度表id'")
	private String tagIds;
	
	@Column(name = "data_type1", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '突发 1是 0不是'")
	private Boolean dataType1;
	
	@Column(name = "data_type2", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '负面 1是 0不是'")
	private Boolean dataType2;
	
	@Column(name = "data_type3", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '游客 1是 0不是'")
	private Boolean dataType3;
	
	@Column(name = "data_type4", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '非游客 1是 0不是'")
	private Boolean dataType4;
	
	@Column(name = "data_type5", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '通用 1是 0不是'")
	private Boolean dataType5;
	
	@Column(name = "comment_count", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '评论数'")
	private Integer commentCount;
	
	@Column(name = "click_count", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '点击数'")
	private Integer clickCount;
	
	@Column(name = "quote_count", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '转发数量'")
	private Integer quoteCount;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "download_date", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下载时间'")
	private Date downloadDate;
	
	@Column(name = "tag_cnt", columnDefinition = "int(11) NOT NULL DEFAULT '1' COMMENT '标签数量'")
	private Integer tagCnt;
	
	@Column(name = "fumiandu_cnt", columnDefinition = "double(10,2) NOT NULL DEFAULT '0.00' COMMENT '负面度比值'")
	private Double fumianduCnt;
	
	@Column(name = "isvalid", columnDefinition = "tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效 1有效 0无效'")
	private Boolean isvalid;
	
	@Column(name = "istoilet", columnDefinition = "tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是厕所数据，1：是， 0不是'")
	private Boolean istoilet;
	
	@Column(name = "event_level", columnDefinition = "tinyint(1) NOT NULL DEFAULT '1' COMMENT '事件级别'")
	private Boolean eventLevel;
	
	@Column(name = "transmissibility_e", columnDefinition = "int(11) NOT NULL DEFAULT '1' COMMENT '突发传播力:媒体权重 * (评论数+点击数+转发数)/30'")
	private Integer transmissibilityE;
	
	@Column(name = "transmissibility_m", columnDefinition = "int(11) NOT NULL DEFAULT '1' COMMENT '媒体传播力:媒体权重'")
	private Integer transmissibilityM;
	
	@Column(name = "highly_relevant", columnDefinition = "bit(1) DEFAULT NULL COMMENT '高相关'")
	private Boolean highlyRelevant;
	
	@Column(name = "tag_good_cnt", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '正面标签数量'")
	private Integer tagGoodCnt;
	
	@Column(name = "tag_bad_cnt", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT '负面标签数量'")
	private Integer tagBadCnt;
	
	@Transient
	private String scenicName;
}

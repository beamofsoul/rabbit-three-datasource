package com.moraydata.general.primary.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

//@JsonIgnoreProperties({"roles"})

@Entity
@Table(name = "T_USER")
//@org.hibernate.annotations.Table(appliesTo = "t_user", comment = "用户表")
public class User extends BaseAbstractEntity {

	private static final long serialVersionUID = -6503407292877788715L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(columnDefinition = "varchar(50) unique not null comment '用户名'")
	private String username;
	
	@Column(columnDefinition = "varchar(100) not null comment '密码'")
	private String password;
	
	@Column(columnDefinition = "varchar(50) comment '昵称'")
	private String nickname;
	
	@Column(columnDefinition = "varchar(20) comment '电子邮箱'")
	private String email;
	
	@Column(columnDefinition = "varchar(20) comment '电话号码'")
	private String phone;
	
	@Column(columnDefinition = "varchar(200) comment '头像照片'")
	private String photo;
	
	@Column(columnDefinition = "varchar(200) comment '用户头像网络地址'")
	private String avatarUrl;
	
	@Column(columnDefinition = "bigint default 0 comment '上级用户ID'")
	private Long parentId;
	
	@Column(columnDefinition = "bigint default 0 comment '创建者'")
	private Long creator;
	
	@Column(columnDefinition = "smallint default 0 comment '邀请码数量'")
	private Integer countOfInvitationCodes;
	
	@Column(columnDefinition = "varchar(20) comment '所属企业'")
	private String company;
	
	@Column(columnDefinition = "varchar(10) comment '企业中职务'")
	private String companyTitle;
	
	@Column(columnDefinition = "varchar(10) comment '企业类型'")
	private String companyType;
	
	@Column(columnDefinition = "varchar(20) comment '企业所在地'")
	private String companyLocation;
	
	@Column(columnDefinition = "varchar(20) comment '企业联系电话'")
	private String companyPhone;
	
	@Column(columnDefinition = "varchar(20) comment '企业传真'")
	private String companyFax;
	
	@Column(columnDefinition = "varchar(64) comment '微信openId'")
	private String openId;
	
	@Column(columnDefinition = "varchar(20) comment '描述'")
	private String description;
	
	@Column(columnDefinition = "varchar(256) comment '可用服务订单细则编号'")
	private String orderItemIds;
	
	@Column(columnDefinition = "tinyint default 1 comment '用户级别'")
	private Integer level;
	
	@Column(columnDefinition = "bit default 1 comment '是否接受通知'")
	private Boolean notified;
	
	@Column(columnDefinition = "enum('non','all','related') not null default 'non' comment '预警舆情接受状态'")
	private String notifiedWarningPublicSentiment;
	
	@Column(columnDefinition = "enum('non','all','related') not null default 'non' comment '热点舆情接受状态'")
	private String notifiedHotPublicSentiment;
	
	@Column(columnDefinition = "enum('non','all','related') not null default 'non' comment '负面舆情接受状态'")
	private String notifiedNegativePublicSentiment;
	
	@Transient
	private String photoString;
	
	@Transient
	private String sceneId; // 2018-04-10 When user using Wechat scanning QR code to login the system, open id is its' user-name and scene id is its' password. The default scene id for scanning QR code to login is 911
	
	@Column(columnDefinition = "int default 1 comment '用户状态 - 1:正常,0:锁定'")
	private Integer status;
	
	@Builder.Default
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.ALL, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_ROLE", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles = new HashSet<Role>(0);
	
	public User(Long id) {
		this.id = id;
	}
	
	public Status getStatusEnum() {
		return Status.getInstance(status);
	}
	
	@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
	public static enum Status {
		NORMAL(1), LOCKED(0);
		@Getter private final int value;
		private static HashMap<Integer, Status> codeValueMap = new HashMap<>(3);
		static {
			for (Status status : Status.values()) {
				codeValueMap.put(status.value, status);
			}
		}
		public static Status getInstance(int code) {
			return codeValueMap.get(code);
		}
	}
	
	@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
	public static enum ResourceType {
		BUTTON("button"), MENU("menu");
		@Getter private final String value;
		private static HashMap<String, ResourceType> codeValueMap = new HashMap<>(3);
		static {
			for (ResourceType resourceType : ResourceType.values()) {
				codeValueMap.put(resourceType.value, resourceType);
			}
		}
		public static ResourceType getInstance(String code) {
			return codeValueMap.get(code);
		}
		public static boolean exists(String code) {
			return codeValueMap.containsKey(code);
		}
	}
	
	@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
	public static enum NotifiedSentiment {
		NON("non"), ALL("all"), RELATED("related");
		@Getter private final String value;
		public static final HashMap<String, NotifiedSentiment> CODE_VALUE_MAP = new HashMap<>(3);
		static {
			for (NotifiedSentiment notifiedSentiment : NotifiedSentiment.values()) {
				CODE_VALUE_MAP.put(notifiedSentiment.value, notifiedSentiment);
			}
		}
		public static NotifiedSentiment getInstance(String code) {
			return CODE_VALUE_MAP.get(code);
		}
		public static boolean exists(String code) {
			return CODE_VALUE_MAP.containsKey(code);
		}
	}
	
	@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
	public static enum Level {
		FIRST(1), SECOND(2), THIRD(3);
		@Getter private final int value;
		private static HashMap<Integer, Level> codeValueMap = new HashMap<>(3);
		static {
			for (Level level : Level.values()) {
				codeValueMap.put(level.value, level);
			}
		}
		public static Level getInstance(int code) {
			return codeValueMap.get(code);
		}
		public static boolean exists(int code) {
			return codeValueMap.containsKey(code);
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", phone=" + phone + ", photo=" + photo + ", avatarUrl=" + avatarUrl
				+ ", parentId=" + parentId + ", creator=" + creator + ", countOfInvitationCodes="
				+ countOfInvitationCodes + ", company=" + company + ", companyTitle=" + companyTitle + ", companyType="
				+ companyType + ", companyLocation=" + companyLocation + ", companyPhone=" + companyPhone
				+ ", companyFax=" + companyFax + ", openId=" + openId + ", description=" + description
				+ ", photoString=" + photoString + ", status=" + status + ", roles=" + roles + ", level=" + level + ", notified=" + notified
				+ ", notifiedWarningPublicSentiment=" + notifiedWarningPublicSentiment + ", notifiedHotPublicSentiment=" + notifiedHotPublicSentiment
				+ ", notifiedNegativePublicSentiment=" + notifiedNegativePublicSentiment + "]";
	}
}

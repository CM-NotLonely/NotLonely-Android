/*
 * FileName: RefreshEvent.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2015-6-8 下午3:06:48
 * last modify author :
 * version : 1.0
 */
package com.efan.notlonely_android.event;

/**
 * @ClassName: RefreshEvent
 * @Description: TODO 刷新事件
 * @author qh@plusub.com
 * @date:
 *     <b>文件创建时间：</b>2015-6-8 下午3:06:48<br>
 *     <b>最后修改时间：</b>2015-6-8 下午3:06:48
 * @version v1.0
 */
public class RefreshEvent {

	public RefreshType type;
	public Object obj;
	public int id;
	
	public RefreshEvent(){}
	public RefreshEvent(RefreshType type){
		this.type = type;
	}
	
	public RefreshEvent(RefreshType type, int id){
		this.type = type;
		this.id = id;
	}
	
	public RefreshEvent(RefreshType type, Object obj){
		this.type = type;
		this.obj = obj;
	}
	
	public RefreshEvent(RefreshType type, Object obj, int id){
		this.type = type;
		this.obj = obj;
		this.id = id;
	}
	
	public enum RefreshType{
		LOGIN,//登录
		ALTERAVATAR//更换头像
	}
}

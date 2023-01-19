/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dows.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel("查询参数对象")
public abstract class QueryParam implements Serializable {
    private static final long serialVersionUID = -3263921252635611410L;

    @ApiModelProperty(value = "页码,默认为1")
	private Integer current = 1;
	@ApiModelProperty(value = "页大小,默认为10")
	private Integer size = 10;

    public void setCurrent(Integer current) {
	    if (current == null || current <= 0){
	        this.current = 1;
        }else{
            this.current = current;
        }
    }

    public void setSize(Integer size) {
	    if (size == null || size <= 0){
	        this.size = 10;
        }else{
            this.size = size;
        }
    }
}

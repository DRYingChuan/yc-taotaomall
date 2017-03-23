package com.yc.taotao.portal.service.impl;

import com.yc.common.utils.HttpClientUtil;
import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItemParamItem;
import com.yc.taotao.portal.service.ItemParamService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by YcDr on 2017/3/20.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    //商品基本服务url
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    //商品规格参数信息url
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;
    @Override
    public String getParamItemById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        TaotaoResult paramItem = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
        TbItemParamItem itemParamItem= (TbItemParamItem) paramItem.getData();
        String param=itemParamItem.getParamData();
        // 把规格参数的json数据转换成java对象
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(param, Map.class);
        // 遍历list生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");
            // 取规格项
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();
    }
}

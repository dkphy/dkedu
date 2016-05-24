/**
 * 加载更多记录<p/>
 * 调用处需自行实现getOneRecordHTML(jsonObj)方法生成HTML代码
 * @param targetId
 */
function loadMore(ctx, targetId, type) {
	var url = null;
	switch(type) {
	case 'info':
		url = ctx + "/ajaxInfo/" + targetId + ".jspx";
		break;
	case 'tag':
		url = ctx + "/ajaxTag/" + targetId + ".jspx";
		break;
	case 'search':
		url = ctx + "/ajaxSearch.jspx";
	}
	var loadCount = 5;
	var offset = $("#offset")[0].innerHTML;
	// ajax post method
	$.post(url,
	{
		q : targetId,
		offset : Number(offset) + 1,
		count : loadCount
	},
	function(data, status) {
		var json = eval(data);
		$.each(json, function(index, item) {
			var oldMsg = $("#show_more_list")[0].innerHTML;
			$("#show_more_list")[0].innerHTML = oldMsg + "<br/>"
					+ getOneRecordHTML(json[index]);
			$("#offset")[0].innerHTML = ++offset;
		});
		if (json.length < loadCount) {
			$(".get_more")[0].innerHTML = "--- 到底了 ---";
		}
	});
}
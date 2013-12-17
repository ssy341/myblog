
var oTable;
$(document).ready(function(){
	initModal();
	oTable = initTable();
	
	//checkbox全选
	$("#checkAll").on("click",function(){
		if($(this).attr("checked")==="checked"){
			$("input[name='checkList']").attr("checked",$(this).attr("checked"));
		}else{
			$("input[name='checkList']").attr("checked",false);
		}
	});
});


function initTable(){
	
	var table =  $("#example").dataTable({
	     	"iDisplayLength":10,
	        'bPaginate': true,      
	        "bDestory":true,
	        "bRetrieve":true,
	        "bFilter":true,
	        "bSort":false,
	        "bProcessing": true,  
				"aaData": [
					[
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-在线调试",
					  "http://www.gbtags.com/gb/debug/new.htm",
					  "<a href='http://www.gbtags.com/gb/debug/new.htm'>进入</a>",
						""
					],
					[
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-live课程",
					  "http://www.gbtags.com/gb/alltrainingcalendar.htm",
					  "<a href='http://www.gbtags.com/gb/alltrainingcalendar.htm'>进入</a>",
						""
					],
					[
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-专题教程",
					  "http://www.gbtags.com/gb/postgroups.htm",
					  "<a href='http://www.gbtags.com/gb/postgroups.htm'>进入</a>",
						""
					],
					 [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-最近动态",
					  "http://www.gbtags.com/gb/explore.htm",
					  "<a href='http://www.gbtags.com/gb/explore.htm'>进入</a>",
						""
					],
					 [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-原创推荐",
					  "http://www.gbtags.com/gb/tag/1.htm",
					  "<a href='http://www.gbtags.com/gb/tag/1.htm'>进入</a>",
						""
					],
					[
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-全部标签",
					  "http://www.gbtags.com/gb/tags.htm",
					  "<a href='http://www.gbtags.com/gb/tags.htm'>进入</a>",
						""
					],
					[
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-加入极客原创翻译小组",
					  "http://www.gbtags.com/gb/share/2446.htm",
					  "<a href='http://www.gbtags.com/gb/share/2446.htm'>进入</a>",
						""
					],
					 [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-关于我们",
					  "http://www.gbtags.com/gb/aboutus.htm",
					  "<a href='http://www.gbtags.com/gb/aboutus.htm'>进入</a>",
						""
					],
					 [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-加入极客社区",
					  "http://www.gbtags.com/gb/requestaccount.htm",
					  "<a href='http://www.gbtags.com/gb/requestaccount.htm'>进入</a>",
						""
					], 
				  [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-申请达人",
					  "http://www.gbtags.com/gb/applydaren.htm",
					  "<a href='http://www.gbtags.com/gb/applydaren.htm'>进入</a>",
						""
					], [
						"<input type='checkbox' name='checkList' value='1'>",
					  "极客标签-广告合作",
					  "http://www.gbtags.com/gb/ads.htm",
					  "<a href='http://www.gbtags.com/gb/ads.htm'>进入</a>",
						""
					]
				  ],
					"aoColumns": [
						{ "sTitle": "<input type='checkbox' id='checkAll'>"},
						{ "sTitle": "标题" },
						{ "sTitle": "地址" },
						{ "sTitle": "操作" },
						{ "sTitle": "操作",
							"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
							$(nTd).html("<a href='javascript:void(0);' onclick='_editFun("+sData+")'>编辑</a>&nbsp;&nbsp;")
							.append("<a href='javascript:void(0);' onclick='_deleteFun("+sData+")'>删除</a>");
				        
							} 
						}
					
					],
	        "sDom": "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
			"sPaginationType": "bootstrap",
			/*"oLanguage": {
				"sUrl": "jsplugin/datatables/zh-CN.txt",
				"sSearch": "快速过滤：" 
			} ,*/
			"fnCreatedRow": function(nRow, aData, iDataIndex){
				//add selected class
	        	$(nRow).click(function(){
	        		if( $(this).hasClass('row_selected') ){
	        			$(this).removeClass('row_selected');
	        		}else{
	        			oTable.$('tr.row_selected').removeClass('row_selected');
	        			$(this).addClass('row_selected');
	        		}
	        	});
	        },
			"fnInitComplete": function(oSettings, json){
				$('<a href="#myModal" class="btn btn-primary" data-toggle="modal">新增</a>'+'&nbsp;'+
				'<a href="#" class="btn btn-primary" id="editFun">修改</a> '+'&nbsp;'+
				'<a href="#" class="btn btn-danger" id="deleteFun">删除</a>'+'&nbsp;').appendTo($('.myBtnBox'));
				//$("#deleteFun").click(_deleteList);
				//$("#editFun").click(_value);
			} 
	     });
	return table;
}

function initModal(){
	$('#myModal').on('show', function(){
		$('body', document).addClass('modal-open');
		$('<div class="modal-backdrop fade in"></div>').appendTo($('body', document));
	});
	$('#myModal').on('hide', function(){
		$('body', document).removeClass('modal-open');
		$('div.modal-backdrop').remove();
	});
}
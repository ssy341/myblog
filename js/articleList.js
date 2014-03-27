var oTable;
$(document).ready(function(){
	
	oTable = initTable();
	
});



function initTable(){
	var table =  $("#articleTable").dataTable({
	     	"iDisplayLength":10,
	        'bPaginate': true,      
	        "bDestory":true,
	        "bRetrieve":true,
	        "bStateSave":true,
	        "bSort":false,
	        "sDom": "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sUrl": "js/zh-CN.txt"
			}
	     });
	return table;
}

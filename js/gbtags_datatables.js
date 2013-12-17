var oTable;
$(document).ready(function(){
	
	oTable = initTable("tableDemo1");
	
});



function initTable(id){
	
	var table =  $("#"+id).dataTable({
	     	"iDisplayLength":5,
	        "sAjaxSource": "http://ssy341.github.io/myblog/src/array.txt",
	        'bPaginate': true,  
	        "bProcessing": true,                   
	        "sDom": "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sUrl": "http://ssy341.github.io/myblog/js/zh-CN.txt"
			},
			"fnInitComplete": function(oSettings, json){
				console.log("init complete");
			} 
	     });
	return table;
}

var oTable;
$(document).ready(function(){
	
	oTable = initTable();
	
});



function initTable(){
	
	var table =  $("#articleTable").dataTable({
	     	"iDisplayLength":10,
	        //"sAjaxSource": "control/dataList.php",
	        //"sAjaxSource":"data/data.json",
	        'bPaginate': true,      
	        "bDestory":true,
	        "bRetrieve":true,
	        //"bFilter":true,
	        "bSort":false,
	        //"bProcessing": true,                    
	        /*"aoColumns": [
	            { "mDataProp": "id",
	            	"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				        $(nTd).html("<input type='checkbox' name='checkList' value='"+sData+"'>");
				        
				    }
				},
	            { "mDataProp": "name" },
	            { "mDataProp": "age" },
	            { "mDataProp": "phone"},
	            { "mDataProp": "id",
	             	"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				        $(nTd).html("<a href='javascript:void(0);' onclick='_editFun("+sData+")'>编辑</a>&nbsp;&nbsp;")
				        .append("<a href='javascript:void(0);' onclick='_deleteFun("+sData+")'>删除</a>");
				        
				    }
	         	},
	        ],*/
	        "sDom": "<'row-fluid'<'span6 myBtnBox'><'span6'f>r>t<'row-fluid'<'span6'i><'span6 'p>>",
			"sPaginationType": "bootstrap"
			/*"oLanguage": {
				"sUrl": "jsplugin/datatables/zh-CN.txt",
				"sSearch": "快速过滤：" 
			} ,*/
			/*"fnCreatedRow": function(nRow, aData, iDataIndex){
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
				$("#deleteFun").click(_deleteList);
				$("#editFun").click(_value);
			} */
	     });
	return table;
}
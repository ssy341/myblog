var options = {
    //initialFrameWidth:860,
    initialFrameHeight:500,
    focus:true
};

var opt = {
    lang: 'zh-cn'
};
UE.utils.extend(opt, options, true);
var editor = UE.getEditor('editor',opt);
<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2014/10/3 0003
 * Time: 8:29
 */
include('Link.php');
include('Category.php');
try{
    $db = new SQLite3('nav.sqlite3');
    if(!$db)
    {
        echo "<h2>Couldn't open the database!</h2>";
        die();
    }
}catch (Exception $e){
    echo $e;
}

//查询所有的类别
$categoryResult=$db->query("SELECT id,name FROM category");

$result = array();
//遍历所有类别
while( $crow=$categoryResult->fetchArray(SQLITE3_ASSOC) ){
    $cid = htmlspecialchars($crow['id']);

    //根据类别查出此类下所有的连接
    $linkResult=$db->query("SELECT title,href,target FROM link where category =".$cid);
    $larray = array();
    while($lrow=$linkResult->fetchArray(SQLITE3_ASSOC)){
        $obj = new Link(htmlspecialchars($lrow['href']),htmlspecialchars($lrow['target']),htmlspecialchars($lrow['title']));
        array_push($larray,$obj);
    }
    $one = new Category(htmlspecialchars($crow['id']),htmlspecialchars($crow['name']),$larray);
    array_push($result,$one);
}
header("Content-type: text/html; charset=utf-8");
echo json_encode($result,JSON_UNESCAPED_UNICODE)

?>
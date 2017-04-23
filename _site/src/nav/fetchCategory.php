<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2014/10/3 0003
 * Time: 8:29
 */
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

$result=$db->query("SELECT id,name FROM category");

$category = array();
while( $row=$result->fetchArray(SQLITE3_ASSOC) )
{
    $obj = new Category(htmlspecialchars($row['id']),htmlspecialchars($row['name']));
    array_push($category,$obj);
    //echo '<a href="'.htmlspecialchars($row['href']).'" target="'.htmlspecialchars($row['target']).'">'.htmlspecialchars($row['title']).'</a>';
}
header("Content-type: text/html; charset=utf-8");
echo json_encode($category,JSON_UNESCAPED_UNICODE);
$db->close();
?>

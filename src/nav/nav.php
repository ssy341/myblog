<?php
    try {
        $db = new SQLite3('nav.sqlite3');
        if (!$db) {
            echo "<h2>Couldn't open the database!</h2>";
            die();
        }
    } catch (Exception $e) {
        echo $e;
    }
    $action = @$_REQUEST['action'];
    if ($action === 'link') {
        $title = @$_REQUEST['title'];
        $href = @$_REQUEST['href'];
        $category = @$_REQUEST['category'];
        $target = '_blanck';
        if($title != null && $href != null && $category != null){
            $db->query("INSERT INTO link (title,href,category,target) VALUES('$title','$href','$category','$target')");
        }else{
            echo "值不完全";
        }

    } else {
        $name = @$_REQUEST['name'];
        if($name != null){
            $db->query("INSERT INTO category (name) VALUES('$name')");
        }else{
            echo "值不完全";
        }
    }
    $db->close();

    header("Location: addLinks.html");
?>

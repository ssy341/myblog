<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2014/10/3 0003
 * Time: 9:46
 */

class Link {
    public $title;
    public $href;
    public $target;

    function __construct($href,$target,$title)
    {
        $this->setTarget( $target);
        $this->setHref($href);
        $this->setTitle($title);
    }

    /**
     * @return mixed
     */
    public function getHref()
    {
        return $this->href;
    }

    /**
     * @param mixed $href
     */
    public function setHref($href)
    {
        $this->href = $href;
    }

    /**
     * @return mixed
     */
    public function getTarget()
    {
        return $this->target;
    }

    /**
     * @param mixed $target
     */
    public function setTarget($target)
    {
        $this->target = $target;
    }

    /**
     * @return mixed
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @param mixed $title
     */
    public function setTitle($title)
    {
        $this->title = $title;
    }

} 
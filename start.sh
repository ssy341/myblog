#!/bin/sh
bundle exec jekyll server
bundle exec jekyll build

 JEKYLL_ENV=production jekyll server
 JEKYLL_ENV=development jekyll server
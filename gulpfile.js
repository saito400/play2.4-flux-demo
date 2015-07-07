var browserify = require('browserify');
var babelify = require('babelify');
var gulp = require('gulp');
var source = require('vinyl-source-stream');
var buffer     = require('vinyl-buffer');
var sourcemaps = require('gulp-sourcemaps');
//var uglify = require('gulp-uglify') // comment out for now because it takes too much time
var watchify   = require('watchify');

gulp.task('build', function() {
    return watchify(browserify({ entries: './app/views/app.jsx', debug: true }, watchify.args))
        .transform(babelify)
        .bundle()
        .pipe(source('bundle.js'))
          .pipe(buffer())
          .pipe(sourcemaps.init({ loadMaps: true  }))
//          .pipe(uglify())
          .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest('./public/javascripts/'));
});

gulp.task('watch', function(){
  gulp.watch('./app/**/*.jsx', ['build']);
});

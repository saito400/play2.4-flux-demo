var c = {
  TODO: {
    LOAD: "LOAD_TODO",
    LOAD_INITIAL: "LOAD_INITIAL_TODO",
    ADD: "ADD_TODO",
    REMOVE: "REMOVE_TODO"
  },

  TODO_TYPE: {
    LOAD: "LOAD_TODO_TYPE",
    ADD: "ADD_TODO_TYPE",
    REMOVE: "REMOVE_TODO_TYPE"
  },

  ROUTE: {
    TRANSITION: "ROUTE:TRANSITION"
  }
};

var methods = {

  todo: {
    load: function(){
      $.ajax({
        url: '/todo/list',
        dataType: 'json',
        success: function(data) {
          this.dispatch(c.TODO.LOAD, data);
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });
    },
    load_initial: function(){
      $.ajax({
        url: '/todotype/list',
        dataType: 'json',
        success: function(data) {
          this.dispatch(c.TODO.LOAD_INITIAL, data);
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(this.props.url, status, err.toString());
        }.bind(this)
      });
    },
    remove: function(id) {

      var sendData = {
        id:id
      }
      $.ajax({
        url: '/todo/delete',
        dataType: 'json',
        method: 'POST',
        data: sendData,
        success: function(data) {
          this.dispatch(c.TODO.REMOVE);
          this.getTodoTypes();
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });

    },
    add: function(payload) {
      $.ajax({
        url: '/todo/create',
        dataType: 'json',
        method: 'POST',
        data: payload,
        success: function(data) {

        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });
    }
  },

  todoType: {
    load: function(){
      $.ajax({
        url: '/todotype/list',
        dataType: 'json',
        success: function(data) {
          this.dispatch(c.TODO_TYPE.LOAD, data);
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });
    },
    remove: function(id) {
      var sendData = {
        id:id
      }
      $.ajax({
        url: '/todotype/delete',
        dataType: 'json',
        method: 'POST',
        data: sendData,
        success: function(data) {
          this.dispatch(c.TODO_TYPE.REMOVE, id);
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });
    },
    add: function(payload) {
      $.ajax({
        url: '/todotype/create',
        dataType: 'json',
        method: 'POST',
        data: payload,
        success: function(data) {

        }.bind(this),
        error: function(xhr, status, err) {
          console.error(status, err.toString());
        }.bind(this)
      });
    }
  },

  routes: {
    transition: function(path, params) {
      this.dispatch(c.ROUTE.TRANSITION, {path: path, params: params});
    }
  }
};

module.exports = {
  methods: methods,
  constants: c
};

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
      this.dispatch(c.TODO.LOAD);
    },
    load_initial: function(){
      this.dispatch(c.TODO.LOAD_INITIAL);
    },
    remove: function(id) {
      this.dispatch(c.TODO.REMOVE, id);
    },
    add: function(payload) {
      this.dispatch(c.TODO.ADD, payload);
    }
  },

  todoType: {
    load: function(){
      this.dispatch(c.TODO_TYPE.LOAD);
    },
    remove: function(id) {
      this.dispatch(c.TODO_TYPE.REMOVE, id);
    },
    add: function(payload) {
      this.dispatch(c.TODO_TYPE.ADD, payload);
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

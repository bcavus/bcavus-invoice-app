
mongo -- "$MONGO_INITDB_DATABASE" <<EOF
    var root_user = '$MONGO_INITDB_ROOT_USERNAME';
    var root_pass = '$MONGO_INITDB_ROOT_PASSWORD';
    var db = db.getSiblingDB('$MONGO_INITDB_DATABASE');
    db.auth(root_user, root_pass);

    var service_user = '$USER_SERVICE_MONGO_DATABASE_USER';
    var service_pass = '$USER_SERVICE_MONGO_DATABASE_PASSWORD';
    var service_db = db.getSiblingDB('$USER_SERVICE_MONGO_DATABASE_NAME');
    service_db.createUser({user: service_user, pwd: service_pass, roles: ["readWrite"]});
EOF
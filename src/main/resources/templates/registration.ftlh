<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация | Synapse</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); height: 100vh; display: flex; align-items: center; }
        .reg-card { width: 100%; max-width: 400px; padding: 2rem; border-radius: 15px; background: white; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
    </style>
</head>
<body>

<div class="container">
    <div class="reg-card mx-auto">
        <h2 class="text-center mb-4 text-primary">Регистрация</h2>

        <form action="/register" method="post">
            <div class="mb-3">
                <label class="form-label text-muted small">Логин</label>
                <input type="text" name="username" class="form-control" placeholder="ivanov123" required>
            </div>
            <div class="mb-4">
                <label class="form-label text-muted small">Пароль</label>
                <input type="password" name="password" class="form-control" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-primary w-100 py-2">Создать аккаунт</button>
        </form>

        <#if errorMessage??>
            <div class="alert alert-danger mt-3 py-2 small text-center">${errorMessage}</div>
        </#if>

        <div class="text-center mt-3">
            <a href="/login" class="text-decoration-none small text-muted">Уже есть аккаунт? Войти</a>
        </div>
    </div>
</div>

</body>
</html>
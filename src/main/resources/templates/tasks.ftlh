<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Synapse | Задачи</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .task-card { transition: transform 0.2s; border: none; border-radius: 10px; }
        .task-card:hover { transform: translateY(-5px); box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="/"><i class="bi bi-cpu-fill"></i> SYNAPSE</a>
        <div class="d-flex align-items-center">
            <span class="text-light me-3">Привет, <strong>${user}</strong>!</span>
            <form action="/logout" method="post" class="m-0">
                <button type="submit" class="btn btn-outline-danger btn-sm">Выйти</button>
            </form>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row mb-4">
        <div class="col-md-8 mx-auto">
            <form action="/" method="get" class="input-group shadow-sm">
                <input type="text" name="title" class="form-control" placeholder="Поиск по названию задачи...">
                <button class="btn btn-primary" type="submit"><i class="bi bi-search"></i> Найти</button>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <h4 class="mb-3 text-secondary">Список задач</h4>
            <#list tasks as task>
                <div class="card task-card mb-3 shadow-sm">
                    <div class="card-body d-flex justify-content-between align-items-center gap-4">
                        <div class="h-auto">
                            <h5 class="card-title mb-2 text-primary text-break">${task.title}</h5>

                            <p class="card-text text-muted mb-0 text-break" style="white-space: pre-wrap;">${task.description}</p>
                        </div>
                        <a href="/task/${task.id}" class="btn btn-outline-primary btn-sm">Подробнее</a>
                    </div>
                </div>
            <#else>
                <div class="alert alert-info">Задач пока нет. Создайте первую!</div>
            </#list>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm sticky-top" style="top: 20px;">
                <div class="card-body">
                    <h5 class="card-title text-success"><i class="bi bi-plus-circle"></i> Новая задача</h5>
                    <form action="/task/create" method="post">
                        <div class="mb-3">
                            <label class="form-label">Название</label>
                            <input type="text" name="title" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Описание</label>
                            <textarea name="description" class="form-control" rows="3"></textarea>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Создать</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
Feature: Gerenciamentos de Produtos

  Scenario: Buscar um produto existente pelo ID
    Given um produto com ID "550e8400-e29b-41d4-a716-446655440000" existe no sistema
    When faço uma requisição GET para "/api/product/550e8400-e29b-41d4-a716-446655440000"
    Then a resposta deve ter status 200
    And deve conter os detalhes do produto

  Scenario: Listar produtos com paginação
    Given produtos estão cadastrados no sistema
    When faço uma requisição GET para listar todos os produtos
    Then a resposta deve ter status 200 e conter a lista de produtos

  Scenario: Criar um novo produto
    Given um novo produto válido
    When faço uma requisição POST para "/api/product"
    Then a resposta deve ter status 200
    And deve conter um ID de produto válido
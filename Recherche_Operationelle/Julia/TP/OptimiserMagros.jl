#Author: Loop Eliott, Eleuteri Mattia, Nobile François, de Crombrugghe Grégoire

using JuMP, Cbc,XLSX,CPUTime

CPUtic()

#------------------------------------------------------------

# change working directory to the one containing this file
#cd(@__DIR__)

instance=XLSX.readxlsx("TP/Instances_TP_RO.xlsx")

ws=instance["Exemple"] # ws = worksheet

sites=split(ws[1,2],",")
magasins=split(ws[2,2],",")
sommets=union(sites,magasins)
aretes=Dict{Array{String,1},Int}() #liste des arêtes avec leurs longueurs
ligne=4
while !ismissing(ws[ligne,1])
    push!(aretes,[ws[ligne,1],ws[ligne,2]]=>ws[ligne,3])
    push!(aretes,[ws[ligne,2],ws[ligne,1]]=>ws[ligne,3])
    global ligne=ligne+1
end
#Trouver les voisins de chaque sommet
voisins=Dict{String,Array{String,1}}()
    for x in sommets
        temp=Array{String,1}()
        for y in sommets
            if(get(aretes,[x,y],0)!=0)
                push!(temp,y)
            end
        end
        push!(voisins,x=>temp)
    end


#Algorithme Dijkstra
println("Start Dijkstra---------------------------")
dijkstra=Dict{Array{String,1},Int}()
for source in sommets
    dist=Dict{String,Int}()
    previous = Dict{String,String}()
    for sommet in sommets
        push!(dist,sommet=>typemax(Int))
        push!(previous,sommet=>"")
    end
    dist[source]=0
    queue=copy(dist)
    while !isempty(queue)
        u = reduce((x, y) -> queue[x] ≤ queue[y] ? x : y, keys(queue))
        #filter!(e->e≠u,queue)
        pop!(queue,u)

        for voisin in voisins[u]
            alt=dist[u] + aretes[[u,voisin]]
            if alt<dist[voisin]
                dist[voisin]=alt
                queue[voisin]=alt
                previous[voisin]=source
            end
        end
    end
    #mettre les trouvailles dans le resultat
    for sommet in sommets
        push!(dijkstra,[source,sommet]=>dist[sommet])
    end
end
println("End Dijkstra---------------------------")
#print(dijkstra)

CPUtoc()

#-----------------------------------------------------------------
CPUtic()
magros=Model(Cbc.Optimizer)
nb_sites=length(sites)
nb_magasin=length(magasins)
@variable(magros,p[i in 1:nb_sites],Bin)
@variable(magros,m[i in 1:nb_magasin,j in 1:nb_sites],Bin)

@objective(magros,Min,(sum(sum(dijkstra[[magasins[i],sites[j]]]*m[i,j]) for i in 1:nb_magasin for j in 1:nb_sites))+(sum(p))*20)


@constraint(magros,cointrainteConstruction,sum(p)>=1)#on doit construire au moins un sites
@constraint(magros,contrainteDeConstruction[i in 1:nb_magasin,j in 1:nb_sites],p[j]>=m[i,j])#si jamais le site n'est pas construit alors pas de chemin vers ce site
@constraint(magros,contrainteDUnicite[i in 1:nb_magasin],sum(m[i,j] for j in 1:nb_sites)==1)#chaque magasin ne peut s'approvisionner chez qu'un seul site

JuMP.optimize!(magros)
#à la fin on affiche seulement ceux qui on été construit
if termination_status(magros) == MOI.OPTIMAL
    println("Solution optimale:")
    for t in 1:nb_sites
        if(JuMP.value(p[t])>=0.5)
            println("construction du site ",sites[t])
            println("relier au magasin :")
            for i in 1:nb_magasin 
                if(JuMP.value(m[i,t])==1)
                    print(magasins[i],"  ")
                end
               
            end
        end
        println()
    end
    println("Coût total: ", JuMP.objective_value(magros))
else
    println("Le solveur a retourné le message ", termination_status(magros))
end
CPUtoc()
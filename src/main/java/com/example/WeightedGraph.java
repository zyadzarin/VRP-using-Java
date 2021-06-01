package com.example;

import java.util.ArrayList;

class WeightedGraph<
    T extends Comparable<T>,
    N extends Comparable<N>
> {

    Vertex<T, N> head;
    int size;

    /**
     * Create an instance of WeightedGraph
     */
    public WeightedGraph() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Remove the head (root vertex) of the graph
     * This will remove every other vertex
     */
    public void clear() {
        this.head = null;
    }

    /**
     * Get the number of vertices contained in this graph
     * @return int
     */
    public int getSize() {
        return this.size;
    }

    public int getIndeg(T vertex) {
        if (this.hasVertex(vertex))
        {
            Vertex<T, N> temp = this.head;

            while (temp != null)
            {
                if (temp.vertexInfo.compareTo(vertex) == 0)
                {
                    return temp.indeg;
                }

                temp = temp.nextVertex;
            }
        }

        return -1;
    }

    public int getOutdeg(T v) {
        if (this.hasVertex(v))
        {
            Vertex<T, N> temp = head;

            while (temp != null)
            {
                if (temp.vertexInfo.compareTo(v) == 0)
                {
                    return temp.outdeg;
                }

                temp = temp.nextVertex;
            }
        }

        return -1;
    }

    public boolean hasVertex(T vertexInfo) {
        if (this.head == null)
        {
            return false;
        }

        Vertex<T, N> tempVertex = this.head;

        while (tempVertex != null)
        {
            if (tempVertex.vertexInfo.compareTo(vertexInfo) == 0)
            {
                return true;
            }

            tempVertex = tempVertex.nextVertex;
        }

        return false;
    }

    public boolean addVertex(T vertexInfo) {
        if (this.hasVertex(vertexInfo))
        {
            return false;
        }

        Vertex<T, N> temp = this.head;
        Vertex<T, N> newVertex = new Vertex<>(vertexInfo, null);

        if (this.head == null)
        {
            this.head = newVertex;
        }

        else
        {
            Vertex<T, N> previous = this.head;

            while (temp != null)
            {
                previous = temp;
                temp = temp.nextVertex;
            }

            previous.nextVertex = newVertex;
        }

        this.size++;

        return true;
    }

    public int getIndex(T vertexInfo) {
        Vertex<T, N> temp = this.head;
        int pos = 0;

        while (temp != null)
        {
            if (temp.vertexInfo.compareTo(vertexInfo) == 0)
            {
                return pos;
            }

            temp = temp.nextVertex;
            pos += 1;
        }

        return -1;
    }

    public ArrayList<T> getAllVertexObjects() {
        ArrayList<T> list = new ArrayList<>();
        Vertex<T, N> temp = this.head;

        while (temp != null)
        {
            list.add(temp.vertexInfo);
            temp = temp.nextVertex;
        }

        return list;
    }

    /**
     * Get a list of all the vertex objects contained inside this graph
     * @return
     */
    public ArrayList<Vertex<T, N>> getAllVertices() {
        ArrayList<Vertex<T, N>> list = new ArrayList<>();
        Vertex<T, N> temp = this.head;

        while (temp != null)
        {
            list.add(temp);
            temp = temp.nextVertex;
        }

        return list;
    }

    /**
     * Get the info of vertex at the specified position
     * @param position
     * @return int
     */
    public T getVertex(int position) {
        if (position > (this.size - 1) || position < 0)
        {
            return null;
        }

        Vertex<T, N> temp = head;

        for (int i=0; i<position; i++)
        {
            temp = temp.nextVertex;
        }

        return temp.vertexInfo;
    }

    /**
     * Add an edge between the vertices with the specified value
     *
     * @param sourceVertexInfo
     * @param destinationVertexInfo
     * @param weight
     * @return
     */
    public boolean addEdge(T sourceVertexInfo, T destinationVertexInfo, N weight)
    {
        if (this.head == null)
        {
            return false;
        }

        if (!this.hasVertex(sourceVertexInfo) || !this.hasVertex(destinationVertexInfo)) {
            return false;
        }

        Vertex<T, N> sourceVertex = this.head;

        while (sourceVertex != null)
        {
            // we found a vertex inside this graph
            // that has the value of sourceVertexInfo
            if (sourceVertex.vertexInfo.compareTo(sourceVertexInfo) == 0)
            {
                // Reached source vertex, look for destination now
                Vertex<T, N> destinationVertex = this.head;

                while (destinationVertex != null)
                {
                    // we found a vertex inside this graph
                    // that has the value of destinationVertexInfo
                    if (destinationVertex.vertexInfo.compareTo(destinationVertexInfo) == 0)
                    {
                        // Reached destination vertex, add edge here
                        Edge<T, N> currentEdge = sourceVertex.firstEdge;
                        Edge<T, N> newEdge = new Edge<>(destinationVertex, weight, currentEdge);

                        sourceVertex.firstEdge = newEdge;
                        sourceVertex.outdeg++;
                        destinationVertex.indeg++;

                        return true;
                    }

                    destinationVertex = destinationVertex.nextVertex;
                }
            }

            sourceVertex = sourceVertex.nextVertex;
        }

        return false;
    }

    /**
     * Find an edge that connects the vertices that have the specified value
     *
     * @param sourceVertexInfo
     * @param destinationVertexInfo
     * @return
     */
    public boolean hasEdge(T sourceVertexInfo, T destinationVertexInfo) {
        if (this.head == null)
        {
            return false;
        }

        if (!this.hasVertex(sourceVertexInfo) || !this.hasVertex(destinationVertexInfo))
        {
            return false;
        }

        Vertex<T, N> sourceVertex = this.head;

        while (sourceVertex != null)
        {
            // found vertex with value sourceVertexInfo
            if (sourceVertex.vertexInfo.compareTo(sourceVertexInfo) == 0)
            {
                Edge<T, N> currentEdge = sourceVertex.firstEdge;

                while (currentEdge != null)
                {
                    // found vertex with value destinationVertexInfo
                    if (currentEdge.toVertex.vertexInfo.compareTo(destinationVertexInfo) == 0)
                    {
                        return true;
                    }

                    currentEdge = currentEdge.nextEdge;
                }
            }

            sourceVertex = sourceVertex.nextVertex;
        }

        return false;
    }

    public N getEdgeWeight(T sourceVertexInfo, T destinationVertexInfo) {
        N notFound = null;

        if (this.head == null)
        {
            return notFound;
        }


        if (!this.hasVertex(sourceVertexInfo) || !this.hasVertex(destinationVertexInfo))
        {
            return notFound;
        }

        Vertex<T, N> sourceVertex = this.head;

        while (sourceVertex != null)
        {
            if (sourceVertex.vertexInfo.compareTo(sourceVertexInfo) == 0)
            {
                // Reached source vertex, look for destination now
                Edge<T, N> currentEdge = sourceVertex.firstEdge;

                while (currentEdge != null)
                {
                    // destination vertex found
                    if (currentEdge.toVertex.vertexInfo.compareTo(destinationVertexInfo) == 0)
                    {
                        return currentEdge.weight;
                    }

                    currentEdge = currentEdge.nextEdge;
                }
            }

            sourceVertex = sourceVertex.nextVertex;
        }

        return notFound;
    }

    /**
     * Get the value of the vertices neighbouring the vertex with specified value
     * @param vertexInfo
     * @return
     */
    public ArrayList<T> getNeighbours(T vertexInfo) {
        if (!this.hasVertex(vertexInfo))
        {
            return null;
        }

        ArrayList<T> list = new ArrayList<T>();
        Vertex<T, N> temp = head;

        while (temp != null)
        {
            if (temp.vertexInfo.compareTo(vertexInfo) == 0)
            {
                // Reached vertex, look for destination now
                Edge<T, N> currentEdge = temp.firstEdge;

                while (currentEdge != null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge = currentEdge.nextEdge;
                }
            }

            temp = temp.nextVertex;
        }

        return list;
    }

    public void printEdges() {
        Vertex<T, N> temp = this.head;

        while (temp != null)
        {
            System.out.print("# " + temp.vertexInfo + " : ");

            Edge<T, N> currentEdge = temp.firstEdge;

            while (currentEdge != null)
            {
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo + "] ");

                currentEdge = currentEdge.nextEdge;
            }

            System.out.println();

            temp = temp.nextVertex;
        }
    }
}
